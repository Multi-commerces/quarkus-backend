package fr.webmaker.data.product;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type(value = "variations")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductVariationData extends BaseResource {

	/**
	 * Référence en boutique
	 */
	private String referenceShop;

	/**
	 * Référence chez le fournisseur
	 */
	private String referenceProvider;

	/**
	 * Nom variation produit
	 */
	private String name;

	/**
	 * Image de representation de la variation produit
	 */
	private byte[] picture;

	/**
	 * Impact sur le prix (HT)
	 */
	private double priceImpact;

	/**
	 * Quantité disponible
	 */
	private int quantity;

	/**
	 * Variation du produit par défaut ?
	 */
	private boolean defaultVariation;

}
