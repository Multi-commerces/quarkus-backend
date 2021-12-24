package fr.commerces.microservices.catalog.products;

import java.util.List;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.products.basic.ProductBasicData;
import fr.commerces.microservices.catalog.products.lang.ProductLangData;
import fr.commerces.microservices.catalog.products.pricing.ProductPricingData;
import fr.commerces.microservices.catalog.products.shipping.ProductShippingData;
import fr.commerces.microservices.catalog.products.stocks.ProductStockData;
import fr.commerces.microservices.catalog.products.variations.ProductVariationData;
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
public class ProductDepth1Data extends ProductData {

	@Relationship(relatedPath = "/variations", value = "variations")
	private List<ProductVariationData> variations;

	@Relationship(relatedPath = "/categories", value = "categories")
	private List<CategoryData> categories;

	@Relationship(relatedPath = "/productLangs", value = "productLangs")
	private List<ProductLangData> productLangs;

	@Relationship(relatedPath = "/shipping", value = "shipping")
	private ProductShippingData shipping;

	@Relationship(relatedPath = "/stock", value = "stock")
	private ProductStockData stock;
	
	@Relationship(relatedPath = "/pricing", value = "pricing")
	private ProductPricingData pricing;
	
	@Relationship(relatedPath = "/basic", value = "basic")
	private ProductBasicData basic;
	
	

}