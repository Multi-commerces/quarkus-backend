package fr.commerces.microservices.catalog.products.relationships.categories;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.basic.CategoryManager;
import fr.commerces.microservices.catalog.products.manager.ProductManager;
import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.webmaker.data.category.CategoryData;

public class ProductCategoryResource extends JsonApiResource<CategoryData> implements ProductCategoryResourceApi {

	@Inject
	ProductManager manager;

	@Inject
	CategoryManager categoryManager;
	
	@Inject
	ProductManager productManager;
	
	public ProductCategoryResource() {
		super(CategoryData.class);
	}
	
	/**
	 * GET CATEGORIES - CATEGORIES (Related)
	 *
	 */
	@Override
	public Response getRelatedProductCategories(long productId) {
		return writeJsonApiResponse(productManager.findById(productId, List.of(ProductRelation.CATEGORIES)).getCategories());
	}
	
	/**
	 * GET PRODUCT - CATEGORIES (RelationShips)
	 *
	 */
	@Override
	public Response getRelationShipsProductCategories(long productId) {
		return writeRelationships(productManager.findById(productId, List.of(ProductRelation.CATEGORIES)).getCategories());
	}

	/**
	 * PATCH PRODUCT - CATEGORIES
	 *
	 */
	@Override
	public void patchRelationShipsProductCategories(long productId, byte[] flux) {
		productManager.replaceCategories(productId, readDocumentAndCollectIds(flux));
	}

	@Override
	public Response deleteRelationShipsProductCategories(long productId, byte[] flux) {
		productManager.removeCategories(productId, readDocumentAndCollectIds(flux));
		
		
		return Response.noContent().build();
	}

}