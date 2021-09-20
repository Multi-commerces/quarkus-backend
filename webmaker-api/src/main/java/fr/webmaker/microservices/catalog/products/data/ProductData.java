package fr.webmaker.microservices.catalog.products.data;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import fr.webmaker.commons.data.ImageData;
import fr.webmaker.commons.data.SimpleData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit 
 * @author Julien ILARI
 *
 */
@Data
@EqualsAndHashCode
public class ProductData implements SimpleData {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Image de couverture
	 */
	private ImageData imageCover = new ImageData();
	
	/**
	 * Image de couverture
	 */
	private List<ImageData> images = new ArrayList<ImageData>();
	
	

	/**
	 * Prix Hors taxe
	 */
	private Double priceHT;

	/**
	 * Taxe
	 */
	private Double taxRule;
	
	@Transient
	public double getPriceTTC()
	{
		return priceHT * (1 + taxRule/100);
	}

}