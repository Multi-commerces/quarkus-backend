package fr.webmaker.microservices.catalog.products.response;

import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductLangData;
import fr.webmaker.microservices.catalog.products.id.ProductLangID;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Réprésente une seul occurence de réponse du produit
 * 
 * @author Julien ILARI 
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductLangResponse extends SingleResponse<ProductLangData, ProductLangID> {

	private static final long serialVersionUID = 1L;
	
	public ProductLangResponse(Long id, String languageCode, ProductLangData data) {
		super(new ProductLangID(id, languageCode), data);
	}

	public ProductLangResponse() {
		this(null, null, null);
	}

	public ProductLangResponse(ProductLangID key, ProductLangData value) {
		super(key, value);
	}

}
