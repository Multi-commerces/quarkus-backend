package fr.commerces.microservices.catalog.products.basic;

import javax.validation.constraints.NotNull;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.commons.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation des informations de base du produit
 * @author Julien ILARI
 *
 */
@Type(value="productBasic", path = ProductBasicResourceApi.PATH)
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductBasicData extends BaseResource {
	/**
	 * Référence (unique) produit
	 */
	@NotNull
	private String reference;
	
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