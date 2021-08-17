package fr.commerces.microservices.catalog.images.enums;

import java.util.stream.Stream;

public enum ShopImageDirectoryType {

	/**
	 * <h1>Type Générale :</h1>
	 * <p>
	 * type d'image dans le cas d'une utilisation par défaut (propre au thème de la boutique)
	 * </p>
	 * <h2>Exemples :</h2>
	 * <ul>
	 * <li>header (1110 x 214 pixels) : l'image d'en-tête</li>
	 * <li>shop : l'image de la boutique</li>
	 * <li>logo (200 x 40 pixels) : logo de la boutique</li>
	 * <li>diapo (1110 x 340 pixels) : Diaporama de la page d'accueil</li>
	 * <li>Favicon (16 x 16 pixels) ou (32 x 32 pixels)
	 * <li>
	 * </ul>
	 */
	GENERAL,

	/**
	 * <h1>Type Produits</h1>
	 */
	PRODUCTS,

	/**
	 * <h1>Type Catégories</h1>
	 */
	CATEGORIES,

	/**
	 * <h1>Type marques</h1>
	 */
	BRANDS,

	/**
	 * <h1>Type Fournisseurs</h1>
	 */
	SUPPLIERS,

	/**
	 * <h1>Type Magasins</h1>
	 */
	STORES;

	public static Stream<ShopImageDirectoryType> stream() {
		return Stream.of(ShopImageDirectoryType.values());
	}
}