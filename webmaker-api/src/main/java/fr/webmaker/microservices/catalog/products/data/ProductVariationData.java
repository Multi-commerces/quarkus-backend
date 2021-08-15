package fr.webmaker.microservices.catalog.products.data;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class ProductVariationData implements Serializable {

	private static final long serialVersionUID = 1L;

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
