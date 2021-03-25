package fr.commerces.services.products.data;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Données relatif au transport du produit
 * @author julien ILARI
 *
 */
@NoArgsConstructor
@Data
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
	private int packageWidth;

	/**
	 * <h1>hauteur</h1>
	 * <p>
	 * unité centimètre (cm)
	 * </p>
	 */
	private int packageHeight;

	/**
	 * <h1>profondeur</h1>
	 * <p>
	 * unité centimètre (cm)
	 * </p>
	 */
	private int packageDepth;

	/**
	 * <h1>poids</h1>
	 * <p>
	 * unité centimètre (kg)
	 * </p>
	 */
	private int packageWeight;

	/*
	 * Delivery
	 * -----------------------------------------------------------------------------
	 * L'affichage du délai de livraison d'un produit est conseillé aux
	 * commerçants vendant en Europe afin de se conformer aux lois locales.
	 * -----------------------------------------------------------------------------
	 */

	private int deliveryTime;
	
	
	/*
	 * Shipping fees
	 * -----------------------------------------------------------------------------
	 * Si un transporteur a une taxe, elle sera ajoutée aux frais d'expédition. Ne
	 * s'applique pas à la livraison gratuite.
	 * -----------------------------------------------------------------------------
	 */
	
	/**
	 * Ce produit entraîne-t-il des frais d'expédition supplémentaires?
	 */
	private double shippingfees;
	
	/*
	 * Available carriers
	 * -----------------------------------------------------------------------------
	 * Si aucun transporteur n'est sélectionné, tous les transporteurs seront
	 * disponibles pour les commandes des clients.
	 * -----------------------------------------------------------------------------
	 */
	
	/**
	 * Transporter disponible pour le produit
	 */
	private String transporter;

}
