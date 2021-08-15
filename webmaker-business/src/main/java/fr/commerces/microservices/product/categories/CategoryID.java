package fr.commerces.microservices.product.categories;

import fr.webmaker.common.Identifier;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CategoryID extends Identifier<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Langue
	 */
	private String languageCode;
	
	public CategoryID() {
		this(null, null);
	}

	public CategoryID(Long id, String languageCode) {
		super();
		this.id = id;
		this.languageCode = languageCode;
	}
	
	

}