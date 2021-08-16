package fr.commerces.microservices.storage;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import fr.commerces.microservices.storage.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class FileSystemStorageService {

	/**
	 * Localisation du répertoire de sauvegarde
	 */
	private Path rootLocation;

	public FileSystemStorageService(
			@ConfigProperty(name = "storage.location", defaultValue = "/upload") String location) {
		this.rootLocation = Paths.get(location);
	}

	/**
	 * 
	 * Quarkus utilise la combinaison des propriétés suivantes pour gérer les
	 * charges utiles en plusieurs parties :
	 * 
	 * quarkus.http.body.handle-file-uploads=true pour déterminer s'il faut ou non
	 * accepter les demandes en plusieurs parties. Selon la documentation, votre
	 * application acceptera toujours les demandes en plusieurs parties, quelle que
	 * soit la valeur définie.
	 * 
	 * quarkus.http.body.uploads-directory le chemin relatif ou absolu où les
	 * téléchargements doivent être conservés
	 * 
	 * quarkus.http.body.delete-uploaded-files-on-end pour contrôler si les fichiers
	 * téléchargés sont effacés à la fin de la demande.
	 * 
	 * quarkus.http.body.preallocate-body-buffer =truepour définir une taille de
	 * tampon pour contenir le corps de la requête, en fonction du nombre dans l'
	 * Content-Lengthen-tête HTTP.
	 * 
	 * @param upload
	 * @param path
	 * @return
	 */
	public String store(String path, String fileName, byte[] content) {
		try {
			final Path location = Paths.get(rootLocation.toString() + "/" + path);
			if (Files.isDirectory(location) != true) {
				try {
					Files.createDirectories(location);
				} catch (IOException e) {
					var message = String.format("Erreur lors de la tentative de création du répertoire %location", location);
					log.error(message);
					throw new StorageException("Could not initialize storage", e);
				}
			}

			final String finalName = new Date().getTime() + "-" + (10000 + new Random().nextInt(100000)) + "-" + fileName;
			Files.copy(new ByteArrayInputStream(content), location.resolve(finalName));
			log.debug(String.format("Enregistrement du fichier %s", finalName));

			return location.resolve(finalName).toString();
		} catch (Exception e) {
			throw new StorageException("Failed to store file " + fileName, e);
		}
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	public byte[] extractBytes(String ImageName) throws IOException {
		// open image
		File imgPath = load(ImageName).toFile();
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
	}
}