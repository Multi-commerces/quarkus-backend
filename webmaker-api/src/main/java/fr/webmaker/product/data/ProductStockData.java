package fr.webmaker.product.data;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor
public class ProductStockData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Quantité en stock
	 */
	private Integer quantity;
	
	/**
	 * Quantité minimum pour la vente
	 */
	private Integer minimumQuantityForSale = 1;
	
	/**
	 * Emplacement du stock
	 */
	private Integer  stockLocation;
	
	/** 
	 * niveau de stock d'alerte
	 */
	private Integer stockLowLevel;
	
	/**
	 * envoyez-moi un e-mail lorsque la quantité est inférieure ou égale à ce niveau
	 */
	private boolean stockLowLevelAlert;
	
	/*
	 * Availability preferences
	 * Comportement en cas de rupture de stock
	 */

}
