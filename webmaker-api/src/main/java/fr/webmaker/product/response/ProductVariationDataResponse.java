package fr.webmaker.product.response;

import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.ProductVariationID;
import fr.webmaker.product.data.ProductVariationData;
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
public class ProductVariationDataResponse extends SingleResponse<ProductVariationData, ProductVariationID>  {

	private static final long serialVersionUID = 1L;


	public ProductVariationDataResponse(Long variationId, Long productId, ProductVariationData data) {
		super();
		this.identifier = new ProductVariationID(variationId, productId);
		this.data = data;
	}



	
	

}
