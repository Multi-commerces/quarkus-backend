package fr.commerces.microservices.catalog.products.variations;

import fr.webmaker.commons.data.SingleCompositeData;
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
public class ProductVariationResponse extends SingleCompositeData<ProductVariationData, ProductVariationID>  {

	private static final long serialVersionUID = 1L;


	public ProductVariationResponse(Long variationId, Long productId, ProductVariationData data) {
		super();
		this.identifier = new ProductVariationID(variationId, productId);
		this.data = data;
	}



	
	

}
