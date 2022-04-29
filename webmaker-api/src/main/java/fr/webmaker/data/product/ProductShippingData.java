package fr.webmaker.data.product;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Données relatif à l'expédition du produit (ProduitLivraison) TODO voir
 * https://developers.google.com/shopping-content/reference/rest/v2.1/products#productshipping
 * 
 * @author julien ILARI
 *
 */
@Type(value = "shipping", path = "/products/{id}/shipping")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductShippingData extends BaseResource {

	/**
	 * Prix ​​d'expédition
	 */
	private double price;

	/*
	 * Package dimension
	 * -----------------------------------------------------------------------------
	 * Dimension du paquet <p>Facturez des frais d'expédition supplémentaires en
	 * fonction des dimensions du paquet couvertes ici.</p>
	 * -----------------------------------------------------------------------------
	 */

	/**
	 * Unité de mesure du package (exemple cm)
	 */
	private String packageUnit;

	/**
	 * <h1>largeur</h1>
	 * <p>
	 * unité centimètre pour packageUnit cm
	 * </p>
	 */
	private double packageWidth;

	/**
	 * <h1>hauteur</h1>
	 * <p>
	 * unité centimètre pour packageUnit cm
	 * </p>
	 */
	private double packageHeight;

	/**
	 * <h1>profondeur</h1>
	 * <p>
	 * unité centimètre pour packageUnit cm
	 * </p>
	 */
	private double packageDepth;

	/**
	 * <h1>poids</h1>
	 * <p>
	 * unité kilo gramme ("g", "kg", "oz", "lb" ...)
	 * </p>
	 */
	private String packageWeightUnit = "Kg";
	private double packageWeightValue;

	/*
	 * Delivery Time (Date de livraison)
	 * -----------------------------------------------------------------------------
	 * L'affichage du délai de livraison d'un produit est conseillé aux commerçants
	 * vendant en Europe afin de se conformer aux lois locales.
	 * -----------------------------------------------------------------------------
	 */

	/**
	 * Délai de livraison des produits en stock
	 */
	private int deliveryTimeQuantityOK;

	@Schema(description = "Délai de livraison des produits en rupture de stock (pour les commandes autorisées)", example = "32")

	private int deliveryTimeQuantityNOK;

	/*
	 * Shipping fees (Frais de port)
	 * -----------------------------------------------------------------------------
	 * Si un transporteur a une taxe, elle sera ajoutée aux frais d'expédition. Ne
	 * s'applique pas à la livraison gratuite.
	 * -----------------------------------------------------------------------------
	 */

	/**
	 * Frais d'expédition supplémentaires
	 */
	private double shippingfees;

	/*
	 * Available carriers
	 * -----------------------------------------------------------------------------
	 * transporteur disponibles pour les commandes sur le produit.
	 * -----------------------------------------------------------------------------
	 */

	/**
	 * 
	 * Transporteurs disponibles pour le produit
	 */
	private List<?> transporters;

}
