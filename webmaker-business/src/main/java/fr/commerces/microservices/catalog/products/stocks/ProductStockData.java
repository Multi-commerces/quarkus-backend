package fr.commerces.microservices.catalog.products.stocks;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.commons.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type("stock")
@Data 
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ProductStockData extends BaseResource {

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
