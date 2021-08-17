package fr.commerces.microservices.catalog.images.modules;

import static fr.commerces.commons.exceptions.utilities.UtilityException.requireNonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;

import com.tinify.Options;
import com.tinify.Tinify;

import fr.commerces.microservices.catalog.images.data.DimentionData;
import fr.commerces.microservices.catalog.images.exceptions.StorageException;
import io.quarkus.runtime.LaunchMode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

/**
 * 
 * @author Julien ILARI
 *
 */
@Slf4j
@UtilityClass
public class UtilityStorage {

	BiFunction<IOException, String, StorageException> storageException = (final IOException e,
			final String partialKey) -> new StorageException("storage.utility" + partialKey + ".exception", e);

	Function<String, StorageException> paramInvalid = (String partialKey) -> new StorageException(
			"utility.storage.param." + partialKey);

	/**
	 * 
	 * @param location
	 * @return
	 */
	public Stream<Path> loadAllPaths(final Path location) {
		requireNonNull.accept("location", location);
		
		try {
			return Files.walk(location, 1).filter(path -> !path.equals(location))
					.map(path -> location.relativize(path));
		} catch (IOException e) {
			throw storageException.apply(e, "Files.walk");
		}
	}

	/**
	 * Suppression
	 * 
	 * @param location
	 */
	public void cleanDirectory(final Path location) {
		requireNonNull.accept("location", location);
		
		try {
			//Files.deleteIfExists(location);
			FileUtils.cleanDirectory(location.toFile());
		} catch (IOException e) {
			throw storageException.apply(e, "Files.cleanDirectory");
		}
	}

	/**
	 * Déplacement du fichier de la source vers la destination, en prenant compte
	 * des options de copie.
	 * 
	 * @param source
	 * @param target
	 * @param fileName
	 */
	public void move(final Path source, final Path dest, final String fileName, CopyOption... copyOption) {
		requireNonNull.accept("source", source);
		requireNonNull.accept("dest", dest);
		requireNonNull.accept("fileName", fileName);
		
		if (Files.isDirectory(source) != true) {
			throw paramInvalid.apply("source.isNotdirectory");
		}

		if (Files.isDirectory(dest) != true) {
			throw paramInvalid.apply("dest.isNotdirectory");
		}

		try {
			Files.move(source.resolve(fileName), dest.resolve(fileName), copyOption);
		} catch (IOException e) {
			throw storageException.apply(e, "Files.move");
		}
	}

	/**
	 * Compression des données de l'image
	 * 
	 * @param sourceData
	 * @param width
	 * @param height
	 * @return
	 */
	public byte[] compressingImages(byte[] sourceData, int width, int height) {
		if (sourceData == null) {
			return null;
		}
		
		if(LaunchMode.current() == LaunchMode.DEVELOPMENT)
		{
			return sourceData;
		}

		final Options options = new Options().with("method", "thumb").with("width", width).with("height", height);
		try {
			return Tinify.fromBuffer(sourceData).resize(options).toBuffer();
		} catch (IOException e) {
			throw storageException.apply(e, "Tinify.resize");
		}
	}

	/**
	 * Copie le flux de données dans un fichier
	 * 
	 * @param location
	 * @param fileName
	 * @param data
	 * @param copyOption
	 * @return
	 */
	public Path copy(Path location, final String fileName, final byte[] data, CopyOption... copyOption) {
		return copy(location, fileName, new ByteArrayInputStream(data), copyOption);
	}

	/**
	 * Opération d'enregistrement du fichier dans le fileSysteme
	 * 
	 * @param location
	 * @param fileName
	 * @param inputStream
	 * @param copyOption
	 * @return
	 */
	public Path copy(final Path location, final String fileName, final InputStream inputStream,
			CopyOption... copyOption) {
		requireNonNull.accept("location", location);
		requireNonNull.accept("fileName", fileName);
		requireNonNull.accept("inputStream", inputStream);

		if (Files.isDirectory(location) != true) {
			throw paramInvalid.apply("location.isNotdirectory");
		}

		// Enregistrement du fichier
		final Path finalLocation = location.resolve(fileName);
		try {
			Files.copy(inputStream, finalLocation, copyOption);
		} catch (IOException e) {
			throw storageException.apply(e, "Files.copy");
		}

		return finalLocation;

	}

	/**
	 * Création du répertoire
	 * 
	 * @param location
	 * @param directory
	 * @return
	 */
	public Path createDirectory(final Path location, final String directory) {
		requireNonNull.accept("location", location);
		requireNonNull.accept("fileName", directory);

		if (Files.isDirectory(location) != true) {
			throw paramInvalid.apply("location.isNotdirectory");
		}

		final Path path = location.resolve(directory);
		if (Files.notExists(path)) {
			try {
				Files.createDirectories(path);
				log.debug("création du répertoire {}", path);
			} catch (IOException e) {
				throw storageException.apply(e, "Files.createDirectories");
			}
		}
		return path;
	}

	/**
	 * Détermine si le flux de données est celui d'une image
	 * 
	 * @param inputStream
	 * @return
	 */
	public boolean isImage(final InputStream inputStream) {
		if (inputStream == null) {
			return false;
		}

		final byte[] data;
		try {
			data = inputStream.readAllBytes();
		} catch (IOException e) {
			return false;
		}

		final String mimeType;
		try {
			mimeType = Magic.getMagicMatch(data, false).getMimeType();
		} catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
			return false;
		}

		if (mimeType.startsWith("image/")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Extraction de la largeur et longueur d'un flux image
	 * 
	 * @param inputStream
	 * @return
	 */
	public DimentionData readWidthHeight(final InputStream inputStream) {
		DimentionData data = new DimentionData();
		if (inputStream == null) {
			return data;
		}

		try {
			final ImageInputStream imageStream = ImageIO.createImageInputStream(inputStream);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
			if (readers.hasNext()) {
				final ImageReader reader = readers.next();
				try {
					reader.setInput(imageStream);
					return new DimentionData(reader.getWidth(reader.getMinIndex()),
							reader.getHeight(reader.getMinIndex()));

				} finally {
					reader.dispose();
				}
			}
		} catch (IOException e) {
			throw storageException.apply(e, "readWidthHeight");
		}

		return data;
	}

	/**
	 * Lecture du fichier
	 * 
	 * @param rootLocation
	 * @param filename
	 * @return
	 */
	public byte[] readAllBytes(final Path rootLocation, String filename) {
		requireNonNull.accept("rootLocation", rootLocation);
		requireNonNull.accept("filename", filename);

		try {
			return Files.readAllBytes(rootLocation.resolve(filename));
		} catch (IOException e) {
			throw storageException.apply(e, "Files.readAllBytes");
		}
	}
}