package fr.webmaker.microservices.catalog.products.id;

import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductID extends Identifier<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Langue
	 */
	protected String languageCode;
	
	public ProductID() {
		this(null, null);
	}

	public ProductID(Long id, String languageCode) {
		super();
		this.id = id;
		this.languageCode = languageCode;
	}
	
	

}