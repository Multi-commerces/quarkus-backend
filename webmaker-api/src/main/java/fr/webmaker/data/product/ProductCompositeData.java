package fr.webmaker.data.product;

import java.util.Collections;
import java.util.List;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.ImageData;
import fr.webmaker.data.category.CategoryData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit avec ces relations
 * 
 * @author Julien ILARI
 *
 */
@Type(value = "product", path = "/products/{id}")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCompositeData extends ProductData {
	
	@Relationship(relatedPath = "/cover", value = "imageCover")
	private ImageData imageCover = new ImageData();

	@Relationship(relatedPath = "/variations", path = "/relationships/variations", value = "variations")
	private List<ProductVariationData> variations = Collections.emptyList();

	@Relationship(relatedPath = "/categories", path = "/relationships/categories", value = "categories")
	private List<CategoryData> categories = Collections.emptyList();

	@Relationship(relatedPath = "/languages", path = "/relationships/languages", value = "productLangs")
	private List<ProductLangData> productLangs = Collections.emptyList();

	@Relationship(relatedPath = "/shipping", path = "/relationships/shipping", value = "shipping")
	private ProductShippingData shipping;

	@Relationship(relatedPath = "/stock", path = "/relationships/stock", value = "stock")
	private ProductStockData stock;
	
	@Relationship(relatedPath = "/pricing", path = "/relationships/pricing", value = "pricing")
	private ProductPricingData pricing;
	
	

}