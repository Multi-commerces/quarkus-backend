package fr.commerces.commons.provider;

import javax.ws.rs.ext.ParamConverter;

import com.neovisionaries.i18n.LanguageCode;

public class LanguageConverter implements ParamConverter<LanguageCode> {

	@Override
	public LanguageCode fromString(String string) {

		return null;
	}

	@Override
	public String toString(LanguageCode t) {
		return "";
	}

}