package fr.webmaker.microservices.catalog.products.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import fr.webmaker.commons.data.ImageData;
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
	private ImageData imageCover = new ImageData();
	
	/**
	 * Image de couverture
	 */
	private List<ImageData> images = new ArrayList<ImageData>();
	
	

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

	/**
	 * Prix Hors taxe
	 */
	private Double priceHT;

	/**
	 * Taxe
	 */
	private Double taxRule;
	
//	@Transient
//	public double getPriceTTC()
//	{
//		return priceHT * (1 + taxRule/100);
//	}

}