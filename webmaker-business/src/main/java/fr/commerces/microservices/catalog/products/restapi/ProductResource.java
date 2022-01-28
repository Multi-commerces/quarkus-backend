package fr.commerces.microservices.catalog.products.restapi;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.basic.CategoryManager;
import fr.commerces.microservices.catalog.products.manager.ProductManager;
import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.restfull.model.PageRequest;

public class ProductResource extends JsonApiResource<ProductData> implements ProductResourceApi {

	@Inject
	ProductManager manager;

	@Inject
	CategoryManager categoryManager;
	
	public ProductResource() {
		super(ProductData.class, ProductCompositeData.class);
	}

	/**
	 * GET ALL
	 */
	@Override
	public Response getJsonApiProducts(final PageRequest page, final List<ProductRelation> include, 
			ProductRelation[] includes, final List<ProductRelation> relationships) {
		return writeResponse(manager.findAll(page, relationships), include);
	}

	/**
	 * GET BY ID
	 *
	 */
	@Override
	public Response getProductById(final long idProduct, final List<ProductRelation> relationships) {
		return writeJsonApiResponse(manager.findById(idProduct, relationships));
	}

	/**
	 * CREATE
	 */
	@Override
	public Response createProduct(byte[] flux) {
		var data = manager.createProduct(readDocument(flux).get());
		return writeResponseCreated(data, data.getId());
	}

	/**
	 * UPDATE
	 */
	@Override
	public Response patchProduct(long productId, byte[] flux) {
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