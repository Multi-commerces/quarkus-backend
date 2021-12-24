package fr.commerces.microservices.catalog.products.lang;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.microservices.catalog.products.id.ProductLangID;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Réprésente une seul occurence de réponse du produit
 * 
 * @author Julien ILARI 
 */
@Type("productLang")
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductLangResponse extends SingleCompositeData<ProductLangData, ProductLangID> {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	public ProductLangResponse(Long id, String languageCode, ProductLangData data) {
		super(new ProductLangID(id, languageCode), data);
		this.id = id.toString();
	}

	public ProductLangResponse(ProductLangID key, ProductLangData value) {
		super(key, value);
		this.id = key.getId().toString();
	}
	
	

}
