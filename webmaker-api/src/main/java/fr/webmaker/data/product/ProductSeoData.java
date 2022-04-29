package fr.webmaker.data.product;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jasminb.jsonapi.annotations.Type;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ProductSeoData (JsonNullable pour mise à jour partielle)
 * @author Julien ILARI
 *
 */
@Type(value = "seo", path = "/products/{id}/seo")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSeoData extends BaseResource {

	/**
	 * Required. The two-letter ISO 639-1 language code for the item.
	 */
	@Schema(readOnly = true)
	private LanguageCode languageCode = LanguageCode.fr;

	@Schema(maxLength = 55, 
			description = "Permettent aux robots de mieux comprendre vos pages et donc la requête sur laquelle vous désirez vous positionner."
					+ "<p>il faut prêter attention à ce qu'elle ne dépasse pas les 55 caractères afin d’être lu par les moteurs de recherche.</p>", 
			implementation = String.class)
	private JsonNullable<String> metaTitle = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasMetaTitle() {
		return metaTitle != null && metaTitle.isPresent();
	}

	@Schema(description = "Contrairement à la balise title, la méta description a pour simple et unique but de séduire vos utilisateurs car cela ne fait plus partie des critères de positionnement par Google.",
			implementation = String.class)
	private JsonNullable<String> metaDescription  = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasMetaDescription() {
		return metaDescription != null && metaDescription.isPresent();
	}
	
	@Schema(description = "URL destinées à améliorer la convivialité et l'accessibilité d'un site Web.",
			implementation = String.class)
	private JsonNullable<String> friendlyURL = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasFriendlyURL() {
		return friendlyURL != null && friendlyURL.isPresent();
	}

}
