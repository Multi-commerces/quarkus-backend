package fr.commerces.microservices.catalog.products.lang;

import javax.validation.constraints.NotNull;

import com.github.jasminb.jsonapi.annotations.Type;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.commons.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit (traduit dans une langue)
 * @author Julien ILARI
 *
 */
@Type("productLang")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductLangData extends BaseResource {
	
	/**
	 * Required. The two-letter ISO 639-1 language code for the item.
	 */
	private LanguageCode languageCode;

	/**
	 * Référence produit
	 */
//	private String reference;
	
	/**
	 * Image de couverture
	 */
//	private ImageData imageCover = new ImageData();
	
	/**
	 * Image de couverture
	 */
//	private List<ImageData> images = new ArrayList<ImageData>();
	
	
	/**
	 * Nom du produit traduit
	 */
	private String name;

	/**
	 * Récapitulatif
	 */
	@NotNull
	private String summary;

	/**
	 * Description
	 */
	private String description;

	/**
	 * Prix Hors taxe
	 */
//	private Double priceHT;

	/**
	 * Taxe
	 */
//	private Double taxRule;
	
//	@Transient
//	public double getPriceTTC()
//	{
//		return priceHT * (1 + taxRule/100);
//	}

}