package fr.webmaker.microservices.catalog.products.response;

import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductVariationData;
import fr.webmaker.microservices.catalog.products.id.ProductVariationID;
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
public class ProductVariationResponse extends SingleResponse<ProductVariationData, ProductVariationID>  {

	private static final long serialVersionUID = 1L;


	public ProductVariationResponse(Long variationId, Long productId, ProductVariationData data) {
		super();
		this.identifier = new ProductVariationID(variationId, productId);
		this.data = data;
	}



	
	

}
