package fr.commerces.services.internal.products.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	 * Image de couverture
	 */
	private ProductImageData imageCover = new ProductImageData();
	
	/**
	 * Image de couverture
	 */
	private List<ProductImageData> images = new ArrayList<ProductImageData>();

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
	
//	@Transient
//	public double getPriceTTC()
//	{
//		return priceHT * (1 + taxRule/100);
//	}

}