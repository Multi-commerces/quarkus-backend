package fr.commerces.microservices.catalog.products.self.model;

import com.neovisionaries.i18n.LanguageCode;

public enum ProductLanguageCode {

	fr(LanguageCode.fr), en(LanguageCode.en);

	private LanguageCode languageCode;

	ProductLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}

	public LanguageCode getLanguageCode() {
		return languageCode;
	}

}