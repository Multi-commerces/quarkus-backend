package fr.commerces.services._transverse.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.commerces.services._transverse.data.LinkData.REL;
import lombok.Getter;
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
	@JsonProperty("_links")
	protected final static List<LinkData> links;
	static {
		links = new ArrayList<>();
		links.add(new LinkData(REL.FIRST, null));
		links.add(new LinkData(REL.NEXT, null));
		links.add(new LinkData(REL.PREV, null));
		links.add(new LinkData(REL.LAST, null));
	}

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
