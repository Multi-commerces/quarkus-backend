package fr.commerces.microservices.catalog.products.self;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.self.model.ProductInclude;
import fr.commerces.microservices.catalog.products.self.model.ProductLanguageCode;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.restfull.model.PageRequest;

/**
 * Api RestFul (HATEOS, spécification JSON:API) pour la gestion des produits
 * 
 * @author Julien ILARI
 *
 */
public final class ProductResource extends JsonApiResource<ProductCompositeData> implements ProductResourceApi {

	@Inject
	ProductManager manager;
	
	@Inject
    Validator validator;
	
	

	public ProductResource() {
		super(ProductCompositeData.class, ProductCompositeData.class);
	}

	/**
	 * GET ALL
	 */
	@Override
	public final Response getJsonApiProducts(PageRequest page, List<ProductLanguageCode> languageCode,
			List<ProductInclude> include) {

		return writeResponse(manager.findAll(page, include), include);
	}

	/**
	 * GET BY ID
	 *
	 */
	@Override
	public final Response getProductById(final Long idProduct, final List<ProductInclude> relationships) {
		return writeResponse(manager.findById(idProduct, relationships), relationships);
	}

	/**
	 * CREATE
	 */
	@Override
	public final Response createProduct(final byte[] flux) {
		var response = manager.createProduct(readData(flux));
		return writeResponseCreated(response, response.getId());
	}

	/**
	 * UPDATE
	 */
	@Override
	public final Response patchProduct(final long productId, final byte[] flux) {
		
		ProductData data = readData(flux);
		return writeResponse(manager.updateProduct(productId, data));
	}

	/**
	 * DELETE BY ID
	 */
	@Override
	public final void deleteProduct(final long productId) {
		manager.deleteProduct(productId);
	}

}