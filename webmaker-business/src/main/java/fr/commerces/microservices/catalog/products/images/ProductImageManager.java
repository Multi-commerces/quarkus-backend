package fr.commerces.microservices.catalog.products.images;

import java.io.InputStream;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import fr.commerces.commons.logged.ManagerInterceptor;
import fr.commerces.microservices.catalog.images.enums.ShopImageDirectoryType;
import fr.commerces.microservices.catalog.images.modules.CatalogStorageManager;

@ManagerInterceptor
public class ProductImageManager {

	/**
	 * Service stockage
	 */
	@Inject
	CatalogStorageManager storageService;
	
	/**
	 * Opération d'enregistrement d'une image produit
	 * @param objId
	 * @param inputStream
	 * @param isCover
	 * @param istTumbnail
	 * @return
	 */
	public Long upload(@NotNull final Long productId, @NotNull final InputStream inputStream, final boolean isCover, final boolean istTumbnail) {		
		storageService.upload(ShopImageDirectoryType.PRODUCTS, productId, inputStream);
		
		
		return null;
		
	}

}