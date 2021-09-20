package fr.webmaker.commons.identifier;

import com.neovisionaries.i18n.LanguageCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data @EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class LangID extends LongID {

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