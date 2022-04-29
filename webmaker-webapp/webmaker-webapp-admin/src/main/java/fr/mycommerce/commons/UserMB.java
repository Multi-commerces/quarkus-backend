package fr.mycommerce.commons;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import com.neovisionaries.i18n.LanguageCode;

@Named(value = "userData")
@SessionScoped
public class UserMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private Locale locale;

	public UserMB() {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		countries = new LinkedHashMap<String, Object>();
		// TODO petit Hack qu'il faudra enlever
		if (locale == Locale.FRENCH) {
			countries.put("French", Locale.FRENCH);
			countries.put("English", Locale.ENGLISH);
		} else {
			countries.put("English", Locale.ENGLISH);
			countries.put("French", Locale.FRENCH);
		}
	}

	public LanguageCode getLanguageCode() {
		return LanguageCode.getByLocale(locale);
	}

	public void changeLanguage(String language) {
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
	}

	private Map<String, Object> countries;

	public Map<String, Object> getCountries() {
		return countries;
	}

	public String getLocaleCode() {
		return LanguageCode.getByLocale(locale).getName();
	}

	public void setLocaleCode(String locale) {
		String newLocaleValue = locale;
		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {
				this.locale = (Locale) entry.getValue();
				FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
			}
		}
	}

	/**
	 * Ecouteur d'événement de changement de la locale
	 * 
	 * @param e événement de changement de valeur
	 */
	public void localeChanged(ValueChangeEvent e) {
		final Object newLocaleValue = e.getNewValue();

		countries.entrySet().stream().map(Entry<String, Object>::getValue).filter(newLocaleValue::equals).findAny()
				.ifPresent(o -> {
					this.locale = (Locale) o;
					FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
				});
	}
}