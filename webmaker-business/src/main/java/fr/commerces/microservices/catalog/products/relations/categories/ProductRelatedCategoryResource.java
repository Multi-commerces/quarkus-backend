package fr.commerces.microservices.catalog.products.relations.categories;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.commerces.microservices.catalog.products.self.ProductManager;
import fr.webmaker.data.category.CategoryData;

public class ProductRelatedCategoryResource extends JsonApiResource<CategoryData>
		implements ProductRelatedCategoryResourceApi {

	@Inject
	ProductManager manager;

	@Inject
	CategoryManager categoryManager;

	@Inject
	ProductManager productManager;

	public ProductRelatedCategoryResource() {
		super(CategoryData.class);
	}

	/**
	 * CATEGORIES (Related)
	 *
	 */
	@Override
	public final Response getRelatedProductCategories(final long productId) {
		var data = categoryManager.findByProductIds(List.of(productId));
		
		return writeResponse(data.values().stream()
				.flatMap(o -> o.stream())
				.collect(Collectors.toUnmodifiableList()));
	}
	
	
	
	
	
	
}