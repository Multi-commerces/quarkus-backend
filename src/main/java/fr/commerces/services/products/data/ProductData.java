package fr.commerces.services.products.data;

import java.io.Serializable;

import com.neovisionaries.i18n.LanguageCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ProductData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reference;

	private LanguageCode languageCode;

	private String name;

	private String summary;

	private String description;

	private double priceHT;

	private double taxRule;

}