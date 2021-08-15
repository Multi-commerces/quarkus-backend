package fr.webmaker.microservices.catalog.products.id;

import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductVariationID extends Identifier<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identifiant du produit
	 */
	protected Long productId;

	public ProductVariationID(final Long variationId, final Long productId) {
		super();
		this.id = variationId;
		this.productId = productId;
	}
	
	/**
	 * Revient au même que getId()
	 * @return
	 */
	public Long getVariationId()
	{
		return id;
	}
	

}