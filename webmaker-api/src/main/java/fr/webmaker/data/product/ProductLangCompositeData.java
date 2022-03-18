package fr.webmaker.data.product;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.product.ProductLangData;
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
public class ProductLangCompositeData extends ProductLangData {

	@Relationship(relatedPath = "../../", value = "product")
	private ProductData product;
	
	@Override
	public String getId()
	{
		return product != null ? product.getId() : null;
	}
	

}