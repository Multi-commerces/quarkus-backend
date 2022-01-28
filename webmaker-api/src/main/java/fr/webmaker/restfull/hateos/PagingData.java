package fr.webmaker.restfull.hateos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@NoArgsConstructor
public class PagingData {

	public static final String JSON_NODE_NUMBER = "number";
	public static final String JSON_NODE_TOTAL_PAGES = "totalPages";
	public static final String JSON_NODE_TOTAL_ELEMENTS = "totalElements";
	public static final String JSON_NODE_SIZE = "size";

	/**
	 * Page en-cours de lecture
	 */
	@JsonProperty(JSON_NODE_NUMBER)
	private long number;

	/**
	 * Taille de la page (nombre d'item pouvant être présent)
	 */
	@JsonProperty(JSON_NODE_TOTAL_PAGES)
	private long totalPages;

	/**
	 * Nombre items total
	 */
	@JsonProperty(JSON_NODE_TOTAL_ELEMENTS)
	private long totalElements;

	/**
	 * Nombre de page total
	 */
	@JsonProperty(JSON_NODE_SIZE)
	private long size;

}
