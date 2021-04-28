package fr.commerces.services.internal.products.data;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit (traduit dans une langue)
 * @author Julien ILARI
 *
 */
@Data
@EqualsAndHashCode
public class ProductData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Référence produit
	 */
	private String reference;

	/**
	 * Nom du produit traduit
	 */
	private String name;

	/**
	 * Résumé
	 */
	private String summary;

	/**
	 * Description
	 */
	private String description;

	/**
	 * Prix Hors taxe
	 */
	private double priceHT;

	/**
	 * Taxe
	 */
	private double taxRule;

}