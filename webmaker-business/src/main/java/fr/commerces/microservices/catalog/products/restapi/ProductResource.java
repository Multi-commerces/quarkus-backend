package fr.commerces.microservices.catalog.products.restapi;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.manager.ProductManager;
import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.restfull.model.PageRequest;

public class ProductResource extends JsonApiResource<ProductCompositeData> implements ProductResourceApi {

	@Inject
	ProductManager manager;
	
	public ProductResource() {
		super(ProductCompositeData.class);
	}

	/**
	 * GET ALL
	 */
	@Override
	public Response getJsonApiProducts(final PageRequest page, final List<ProductRelation> relationships) {
		return writeResponse(manager.findAll(page, relationships), relationships);
	}
	
	/**
	 * GET CATEGORIES - CATEGORIES (Related)
	 *
	 */
	@Override
	public Response getRelatedProductCategories(long productId) {
		return writeJsonApiResponse(manager.findById(productId, List.of(ProductRelation.CATEGORIES)).getCategories());
	}

	/**
	 * GET BY ID
	 *
	 */
	@Override
	public Response getProductById(final Long idProduct, final List<ProductRelation> relationships) {
		return writeResponse(manager.findById(idProduct, relationships), Collections.emptyList());
	}

	/**
	 * CREATE
	 */
	@Override
	public Response createProduct(final byte[] flux) {
		var data = readDocument(flux).get();		
		var response = manager.createProduct(data);
		
		
		return writeResponseCreated(response, response.getId());
	}

	/**
	 * UPDATE
	 */
	@Override
	public Response patchProduct(final long productId, final byte[] flux) {
		return writeJsonApiResponse(manager.updateProduct(productId, readDocument(flux).get()));
	}
	
	/**
	 * DELETE BY ID
	 */
	@Override
	public void deleteProduct(final long productId) {
		manager.deleteProduct(productId);
	}

}