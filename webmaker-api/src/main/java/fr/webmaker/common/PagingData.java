package fr.webmaker.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Informations de pagination.
 * <p>
 * Il est possible que la liste remontée par le serveur comporte un très grand
 * nombre d’éléments. Afin d’éviter par exemple une surcharge, la pagination
 * permet de réduire le nombre d’éléments remontés par l’API.
 * </p>
 * 
 * @author Julien ILARI
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class PagingData {

	/**
	 * Page en-cours de lecture
	 */
	long page;

	/**
	 * Taille de la page (nombre item pouvant être présent)
	 */
	long pageSize;

	/**
	 * Navigation
	 * <ul>
	 * <li>first</li>
	 * <li>next</li>
	 * <li>prev</li>
	 * <li>last</li>
	 * </ul>
	 */
//	protected final static List<LinkData> _links;
//	static {
//		_links = new ArrayList<>();
//		_links.add(new LinkData(REL.FIRST, null));
//		_links.add(new LinkData(REL.NEXT, null));
//		_links.add(new LinkData(REL.PREV, null));
//		_links.add(new LinkData(REL.LAST, null));
//	}

	/*
	 * ============================================ TOTAL
	 * ============================================
	 */

	/**
	 * Nombre items total
	 */
	long totalItems;

	/**
	 * Nombre de page total
	 */
	long totalPages;

}
