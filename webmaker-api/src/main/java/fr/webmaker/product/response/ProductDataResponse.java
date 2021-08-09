package fr.webmaker.product.response;

import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductData;
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
public class ProductDataResponse extends SingleResponse<ProductData, ProductID>  {

	private static final long serialVersionUID = 1L;


	public ProductDataResponse(Long id, String languageCode, ProductData data) {
		super();
		this.identifier = new ProductID(id, languageCode);
		this.data = data;
	}


	public ProductDataResponse() {
		this(null, null, null);
	}
	
	

}
