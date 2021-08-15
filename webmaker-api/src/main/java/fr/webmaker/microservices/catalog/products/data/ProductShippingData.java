package fr.webmaker.microservices.catalog.products.data;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Données relatif à l'expédition du produit
 * @author julien ILARI
 *
 */
@NoArgsConstructor
@Getter @Setter @ToString
public class ProductShippingData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Package dimension
	 * -----------------------------------------------------------------------------
	 * Dimension du paquet <p>Facturez des frais d'expédition supplémentaires
	 * en fonction des dimensions du paquet couvertes ici.</p>
	 * -----------------------------------------------------------------------------
	 */

	/**
	 * <h1>largeur</h1>
	 * <p>
	 * unité centimètre (cm)
	 * </p>
	 */
	private double packageWidth;

	/**
	 * <h1>hauteur</h1>
	 * <p>
	 * unité centimètre (cm)
	 * </p>
	 */
	private double packageHeight;

	/**
	 * <h1>profondeur</h1>
	 * <p>
	 * unité centimètre (cm)
	 * </p>
	 */
	private double packageDepth;

	/**
	 * <h1>poids</h1>
	 * <p>
	 * unité kilo gramme (kg)
	 * </p>
	 */
	private double packageWeight;

	/*
	 * Delivery Time (Date de livraison)
	 * -----------------------------------------------------------------------------
	 * L'affichage du délai de livraison d'un produit est conseillé aux
	 * commerçants vendant en Europe afin de se conformer aux lois locales.
	 * -----------------------------------------------------------------------------
	 */

	/**
	 * Délai de livraison des produits en stock
	 */
	private int deliveryTimeQuantityOK;
	
	/**
	 * Délai de livraison des produits en rupture de stock (pour les commandes autorisées)
	 */
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
