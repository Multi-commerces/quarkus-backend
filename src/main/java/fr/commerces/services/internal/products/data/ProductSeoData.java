package fr.commerces.services.internal.products.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSeoData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Permettent aux robots de mieux comprendre vos pages et donc la
	 * requête sur laquelle vous désirez vous positionner.
	 * <p>
	 * il faut prêter attention à ce qu'elle ne dépasse pas les 55 caractères afin
	 * d’être lu par les moteurs de recherche.
	 * </p>
	 */
	private String metatitle;

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
