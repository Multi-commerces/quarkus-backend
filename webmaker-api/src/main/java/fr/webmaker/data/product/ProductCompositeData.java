package fr.webmaker.data.product;

import java.util.List;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.ImageData;
import fr.webmaker.data.category.CategoryData;
import lombok.Getter;
import lombok.Setter;

/**
 * Représentation d'un produit avec ces relations
 * https://wpml.org/documentation/related-projects/woocommerce-multilingual/using-wordpress-rest-api-woocommerce-multilingual/
 * @author Julien ILARI
 *
 */
@Getter @Setter
@Type(value = "product", path = "/products/{id}")
public final class ProductCompositeData extends ProductData {	

	@Relationship(relatedPath = "/cover", value = "imageCover")
	private ImageData imageCover;

	@Relationship(relatedPath = "/variations", path = "/relationships/variations", 
			resolve = true,
			value = "variations")
	private List<ProductVariationData> variations;

	@Relationship(relatedPath = "/categories", path = "/relationships/categories", 
			resolve = true,
			value = "categories")
	private List<CategoryData> categories;


	@Relationship(relatedPath = "/languages", path = "/relationships/languages", value = "languages")
	private List<ProductLangCompositeData> languages;
	

	@Relationship(relatedPath = "/shipping", value = "shipping", resolve = true)
	private ProductShippingData shipping;

	@Relationship(relatedPath = "/stock", value = "stock", resolve = true)
	private ProductStockData stock;

	@Relationship(relatedPath = "/pricing", value = "pricing", resolve = true)
	private ProductPricingData pricing;

}