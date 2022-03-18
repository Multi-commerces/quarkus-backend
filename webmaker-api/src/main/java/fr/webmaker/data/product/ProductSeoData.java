package fr.webmaker.data.product;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type(value = "seo", path="/products/{id}/seo")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductSeoData extends BaseResource {
	
	/**
	 * Permettent aux robots de mieux comprendre vos pages et donc la
	 * requête sur laquelle vous désirez vous positionner.
	 * <p>
	 * il faut prêter attention à ce qu'elle ne dépasse pas les 55 caractères afin
	 * d’être lu par les moteurs de recherche.
	 * </p>
	 */
	private String metaTitle;

	/**
	 * Contrairement à la balise title, la méta description a pour simple et unique
	 * but de séduire vos utilisateurs car cela ne fait plus partie des critères de
	 * positionnement par Google
	 */
	private String metaDescription;

	/**
	 * URL destinées à améliorer la convivialité et l'accessibilité d'un site Web
	 */
	private String friendlyURL;

}
