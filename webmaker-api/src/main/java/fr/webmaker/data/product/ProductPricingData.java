package fr.webmaker.data.product;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type(value = "pricing", path="/products/{id}/pricing")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductPricingData extends BaseResource {


	/**
	 * Price (tax excl.)
	 */
	private double priceHT;

	/**
	 * Price (tax incl.)
	 */
	private double priceTTC;

	/**
	 * Prix unitaire (tax excl.)
	 */
	private double priceUnit;

	/**
	 * Va de paire avec le prix de base (par exemple, prix au kg, au litre, au
	 * mètre).
	 */
	private String priceUnitText;

	/**
	 * Règle fiscale (TVA)
	 * 
	 */
	private double taxRule;

	/**
	 * en solde ?
	 */
	private boolean sale;

	/**
	 * <h1>prix de revient (tax excl.)</h1>
	 * 
	 * <p>
	 * prix que vous avez payé pour le produit. N'incluez pas la taxe. Il doit être
	 * inférieur au prix de vente net: la différence entre les deux sera votre
	 * marge.
	 * <p>
	 */
	private double costPrice;

	/**
	 * <h1>prix spécifiques</h1>
	 * <p>
	 * prix pour les clients appartenant à différents groupes, différents pays, etc.
	 * </p>
	 */
	private double[] specificPrices;

}
