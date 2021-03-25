package fr.commerces.services.products.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductVariationData {


	private Long id;

	/**
	 * Identifiant du produit variation
	 */
	private Long productId;
	
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
