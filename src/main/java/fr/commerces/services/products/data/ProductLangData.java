package fr.commerces.services.products.data;

import java.io.Serializable;

import com.neovisionaries.i18n.LanguageCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Produit dans une langue (exemple FR pour france)
 * 
 * @author Julien ILARI
 *
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductLangData extends ProductData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code de la langue
	 */
	private LanguageCode languageCode;

	/**
	 * Nom du produit traduit
	 */
	private String name;

	/**
	 * Description du produit traduit
	 */
	private String description;

	/**
	 * Description courte du produit traduit
	 */
	private String descriptionShort;


}
