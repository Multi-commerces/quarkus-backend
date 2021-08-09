package fr.webmaker.product.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation des informations de base du produit
 * @author Julien ILARI
 *
 */
@Data
@EqualsAndHashCode
public class ProductBasicData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Référence (unique) produit
	 */
	@NotNull
	private String reference;

	/**
	 * Nom du produit 
	 */
	@NotNull
	private String name;

	/**
	 * Résumé
	 */
	@NotNull
	private String summary;

	/**
	 * Description
	 */
	@NotNull
	private String description;
	
	/**
	 * Quantité du produit
	 */
	private double quantity;

	/**
	 * Prix Hors taxe
	 */
	private double priceHT;

	/**
	 * Taxe
	 */
	private double taxRule;

}