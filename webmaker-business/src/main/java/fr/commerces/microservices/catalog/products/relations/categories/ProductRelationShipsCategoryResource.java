package fr.commerces.microservices.catalog.products.relations.categories;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.commerces.microservices.catalog.products.self.ProductManager;
import fr.commerces.microservices.catalog.products.self.model.ProductInclude;
import fr.webmaker.data.category.CategoryData;

public class ProductRelationShipsCategoryResource extends JsonApiResource<CategoryData>
		implements ProductRelationShipsCategoryResourceApi {

	@Inject
	ProductManager manager;

	@Inject
	CategoryManager categoryManager;

	@Inject
	ProductManager productManager;

	public ProductRelationShipsCategoryResource() {
		super(CategoryData.class);
	}


	/**
	 * GET PRODUCT - CATEGORIES (RelationShips)
	 *
	 */
	@Override
	public Response getRelationShipsProductCategories(long productId) {
		return writeRelationships(
				productManager.findById(productId, List.of(ProductInclude.CATEGORIES)).getCategories());
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