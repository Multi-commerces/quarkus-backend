package fr.webmaker.commons.identifier;

import com.neovisionaries.i18n.LanguageCode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class LangID extends Identifier<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Langue
	 */
	private LanguageCode languageCode;
	
	public LangID() {
		this(null, null);
	}

	public LangID(Long id, LanguageCode languageCode) {
		super();
		this.id = id;
		this.languageCode = languageCode;
	}
	
	

}