
package fr.webmaker.data.product;

import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.github.jasminb.jsonapi.annotations.Type;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Représentation d'un produit (traduit dans une langue)
 * 
 * @author Julien ILARI
 *
 */
@Schema(name = "data", description = "les données")
@Type(value = "productLang", path = "/products/{id}")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductLangData extends BaseResource {

	@Schema(readOnly = true)
	private Long productId;

	/**
	 * Required. The two-letter ISO 639-1 language code for the item.
	 */
	@Schema(readOnly = true)
	private LanguageCode languageCode = LanguageCode.undefined;

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
	
}