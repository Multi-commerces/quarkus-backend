package fr.webmaker.microservices.catalog.products.response;

import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductData;
import fr.webmaker.microservices.catalog.products.id.ProductID;
import lombok.Getter;
import lombok.Setter;

/**
 * Réprésente une seul occurence de réponse
 * @author Julien ILARI
 *public class SingleResponse<M, I extends ProductID> implements Serializable {
 * @param <M> Data
 * @param <I> Identifier
 */
@Getter @Setter
public class ProductResponse extends SingleResponse<ProductData, ProductID>  {

	private static final long serialVersionUID = 1L;


	public ProductResponse(Long id, String languageCode, ProductData data) {
		super();
		this.identifier = new ProductID(id, languageCode);
		this.data = data;
	}


	public ProductResponse() {
		this(null, null, null);
	}
	
	

}
