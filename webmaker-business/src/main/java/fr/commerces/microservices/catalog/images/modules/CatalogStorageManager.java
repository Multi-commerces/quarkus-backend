package fr.commerces.microservices.catalog.images.modules;

import static fr.commerces.microservices.catalog.images.enums.ShopImageDirectoryType.PRODUCTS;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.enterprise.event.Observes;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.MDC;

import fr.commerces.microservices.catalog.images.entities.ShopImage;
import fr.commerces.microservices.catalog.images.entities.ShopImageDimentionConfig;
import fr.commerces.microservices.catalog.images.enums.ShopImageDirectoryType;
import fr.commerces.microservices.catalog.images.exceptions.StorageException;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductImage;
import fr.webmaker.annotation.ManagerInterceptor;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.ShutdownEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Julien ILARI
 *
 */
@Slf4j
@ManagerInterceptor
//@Startup
//@ApplicationScoped
public class CatalogStorageManager {

	@Inject
	UserTransaction transaction;

	@Inject
	EntityManager em;

	private final boolean initialized;

	/**
	 * Localisation du répertoire de sauvegarde.
	 */
	private Path rootLocation;

	/**
	 * Sous-répertoires du répertoire de sauvegarde.
	 */
	private final static Map<ShopImageDirectoryType, Path> locationByImageType = new HashMap<>();

	private final static Map<ShopImageDirectoryType, Path> pathOriginalByImageType = new HashMap<>();
	private final static Map<ShopImageDirectoryType, Path> pathSynchroByDirectoryType = new HashMap<>();
	private final static Map<ShopImageDirectoryType, Path> pathResizingByImageType = new HashMap<>();

	void onStop(@Observes ShutdownEvent ev) {
		if (LaunchMode.current() == LaunchMode.DEVELOPMENT) {
			log.info("(ShutdownEvent) Opération de nétoyage pour le mode DEVELOPMENT...");
			final Path pathOriginal = pathOriginalByImageType.get(PRODUCTS),
					pathResizing = pathResizingByImageType.get(PRODUCTS);

			UtilityStorage.cleanDirectory(pathOriginal);
			UtilityStorage.cleanDirectory(pathResizing);
		}
	}

	/**
	 * Initilisation des répertoires et du service Tinify pour la compression des
	 * images.
	 * 
	 * @param location
	 */
	CatalogStorageManager(@ConfigProperty(name = "storage.location") final String location,
			@ConfigProperty(name = "storage.tinify.setKey") final String tinifyKey) {
		if (isBlank(location)) {
			log.warn("storage.location.empty");
		}
		if (isBlank(tinifyKey)) {
			log.warn("storage.tinify.setKey.empty");
		}

		// répertoire racine
		this.rootLocation = Paths.get(location);

		/*
		 * Création des répertoires et sous-répertoire (si non existant)
		 */
		ShopImageDirectoryType.stream().forEach(imageType -> {
			final Path imageTypeLocation = UtilityStorage.createDirectory(rootLocation,
					imageType.toString().toLowerCase());
			locationByImageType.put(imageType, imageTypeLocation);

			pathOriginalByImageType.put(imageType, UtilityStorage.createDirectory(imageTypeLocation, "original"));
			pathSynchroByDirectoryType.put(imageType, UtilityStorage.createDirectory(imageTypeLocation, "synchro"));
			pathResizingByImageType.put(imageType, UtilityStorage.createDirectory(imageTypeLocation, "resized"));
		});

		// demande à la librairie imageIO de ne pas utiliser le système de cache
		ImageIO.setUseCache(false);

		// Configuration tinifyKey afin de pouvoir être authorisé a utiliser api Tinify
//		Tinify.setKey(tinifyKey);

		initialized = true;
	}

	public String upload(final ShopImageDirectoryType objRefType, final Long objId, final InputStream inputStream,
			final boolean... is) {

		boolean isCover = is != null ? is[0] : false;
		boolean istTumbnail = is != null && is.length == 2 ? is[1] : false;

		return upload(objRefType, objId, inputStream, isCover, istTumbnail);
	}

	/**
	 * Opération d'enregistrement du fichier dans le fileSysteme du serveur
	 * 
	 * @param path     chemin du répertoire (exemple : products
	 * @param fileName
	 * @param content
	 * @return
	 */
	public String upload(final ShopImageDirectoryType objRefType, final Long objId, final InputStream inputStream,
			final boolean isCover, final boolean istTumbnail) {
		if (!initialized) {
			// TODO : lancer une exception
			return "Service de stockage des images non initialiser.";
		}
		if (!UtilityStorage.isImage(inputStream)) {
			// TODO : lancer une exception
			return "le fichier n'est pas une image";
		}

		// Identification du path
		final Path path = locationByImageType.get(objRefType);

		// final DimentionData dimention =
		// UtilityStorage.extractDimentionData(inputStream);

		// Génération d'un nom de fichier afin d'éviter les conflits
		StringBuilder fileName = new StringBuilder("product" + String.valueOf(objId)).append("-")
				.append("image" + (10000 + new Random().nextInt(100000)));
		if (isCover) {
			fileName.append("-cover");
		}

		if (istTumbnail) {
			fileName.append("-tumbnail");
		}
		fileName.append(".jpg");

		// Enregistrement du fichier
		return UtilityStorage.copy(path, fileName.toString(), inputStream).toString();
	}

	public Map<ShopImageDirectoryType, Path> generateImages(List<ShopImageDirectoryType> imageTypes, Path path) {
		final Map<ShopImageDirectoryType, Path> pathByImageType = new HashMap<>();

		return pathByImageType;
	}

	@Transactional
	public boolean synchroProduct() {
		StringBuilder header = new StringBuilder();
		header.append("\n ###########################################################");
		header.append("\n ############ SYNCHRO - Launch Mode {} ############");
		header.append("\n ###########################################################");
		log.debug(header.toString(), LaunchMode.current());
		
		/*
		 * Préparation avant synchronisation et compression des images contenu dans le
		 * répertoire cible.
		 */
		final Path pathSynchro = pathSynchroByDirectoryType.get(PRODUCTS),
				pathOriginal = pathOriginalByImageType.get(PRODUCTS),
				pathResizing = pathResizingByImageType.get(PRODUCTS);

		log.debug("Lecture du repertoire de synchro");
		UtilityStorage.loadAllPaths(pathSynchro).forEach(o -> {
			final String fileName = o.getFileName().toString();
			MDC.put("fileName", fileName);

			final String[] fileNameSplit = split(fileName, '.');
			if (fileNameSplit.length < 2)
				return;
			final String[] fileNameTab = split(fileNameSplit[0], '-');
			final String extensionFileName = fileNameSplit[1];

			try {
				/*
				 * Identifiant produit (obligatoire) et image (optionnel)
				 */
				final Long objId;
				try {
					Object value = fileNameTab[0];
					MDC.put("objId", value);
					objId = Long.valueOf(fileNameTab[0]); // Obligatoire
				} catch (Exception e) {
					throw new StorageException("storage.manager.objid.invalidFormat");
				}

				// Recherche du Produit
				final Product product = Product.<Product>findByIdOptional(objId)
						.orElseThrow(() -> new StorageException("storage.manager.product.notExist"));
				
				final byte[] originalImage = UtilityStorage.readAllBytes(pathSynchro, fileName);

				log.debug("Lecture des configurations");
				ShopImageDimentionConfig.<ShopImageDimentionConfig>findAll().stream()
						.filter(ShopImageDimentionConfig::isProdutcs).forEach(config -> {
							MDC.put("configName", config.getName());

							// Préparation (Construction du nom du fichier) et du répertoire parent (porte
							// l'identifiant)
							final StringBuilder fileNameSb = new StringBuilder(String.valueOf(objId)).append("-")
									.append(config.getName()).append("-")
									.append(config.getDeviceType().name().toLowerCase()).append(".")
									.append(extensionFileName);

							final Path pathObjId = UtilityStorage.createDirectory(pathResizing, String.valueOf(objId));

							/*
							 * Compression => Update BDD => Update FileSysteme
							 */
							try {
								/*
								 * Opération de compression du fichier
								 */
								log.debug("Tentative de compression de l'image (largeur:{}px;longeur:{}px)",
										config.getWidth(), config.getHeight());

								final byte[] compressed = UtilityStorage.compressingImages(originalImage,
										config.getWidth(), config.getHeight());
								if (compressed == null)
									return;

								/*
								 * Enregistrement du fichier compressé.
								 */
								UtilityStorage.copy(pathObjId, fileNameSb.toString(), compressed,
										StandardCopyOption.REPLACE_EXISTING);

								

								/*
								 * Gestion (des infos) de l'image en BDD
								 */
								final LocalDateTime toDay = LocalDateTime.now(ZoneOffset.UTC);
								
								// Image
								final ShopImage image = ShopImage.findByFileName(fileNameSb.toString())
										.singleResultOptional().orElseGet(ShopImage::new);
								image.setUpdated(toDay);
								image.setDimentionConfig(config);
								// image.setImage(compressed);

								// Product Image (Mode création seulement)
								if (!image.isPersistent()) {
									image.setObjId(objId);
									image.setCreated(toDay);
									image.setFileName(fileNameSb.toString());
									image.setCaption("synchro " + product.getImages().size());
									image.setName("synchro");
									try {
										final ProductImage productImage = new ProductImage();
										productImage.setImage(image);
										productImage.setProduct(product);
										productImage.setPosition(product.getImages().size() + 1);
										
										product.getImages().add(productImage);

										productImage.persist();
									} catch (Exception e) {
										log.error("storage.manager.image.persist.exception");
										throw e;
									}
								}

							} catch (Exception e) {
								log.error(e.getMessage());
								MDC.remove("configName");
								return;
							}
						});

				/*
				 * déplacement de ./synchro/* vers ./original/*
				 * 
				 */
				if (LaunchMode.current() != LaunchMode.DEVELOPMENT) {
					UtilityStorage.move(pathSynchro, pathOriginal, fileName, StandardCopyOption.REPLACE_EXISTING);
				}

			} catch (Exception e) {
				// TODO: handle exception
				log.error(e.getMessage());
				MDC.remove("fileName");
				MDC.remove("objId");
				MDC.remove("configName");
				return;
			}

		});

		return true;
	}

}