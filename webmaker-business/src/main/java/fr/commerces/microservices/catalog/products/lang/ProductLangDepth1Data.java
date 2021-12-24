package fr.commerces.microservices.catalog.products.lang;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.commerces.microservices.catalog.products.ProductData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit (traduit dans une langue)
 * 
 * @author Julien ILARI
 *
 */
@Type(value="productLang", path = "/products/{id}")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductLangDepth1Data extends ProductLangData {

	@Relationship(path = "/product", value = "product")
	private ProductData product;
	


}