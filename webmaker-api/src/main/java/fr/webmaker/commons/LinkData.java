package fr.webmaker.commons;

import java.util.List;

import javax.ws.rs.HttpMethod;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LinkData {

	/**
	 * l’attribut rel permet de donner un sens aux URLs et de faciliter la gestion
	 * côté client
	 */
	private String rel;

	/**
	 * Lien vers la ressource (exemple : http://localhost:port/ressource)
	 */
	private String href;

	/**
	 * <h1>Verbe Http à utiliser pour consommer la ressource</h1>
	 * <h2>Tous les types d'opérations HTTP possibles pour ce chemin</h2>
	 * <ul>
	 * <li>POST</li>
	 * <li>GET</li>
	 * <li>PUT</li>
	 * <li>PATCH</li>
	 * <li>DELETE</li>
	 * <li>HEAD</li>
	 * <li>OPTIONS</li>
	 * <li>TRACE</li>
	 * <li>UPDATE</li>
	 * </ul>
	 */
	private String method;

	private String desc;

	private String summary;

	/**
	 * Liste de paramètres
	 */
	private List<Object> params;

	public LinkData() {
		this((String) null, null);
	}

	public LinkData(String href) {
		this(REL.SELF, href);
	}

	public LinkData(REL rel, String href) {
		this(rel.name(), href);
	}

	public LinkData(String rel, String href) {
		super();
		this.rel = rel;
		this.href = href;
		this.method = HttpMethod.GET;
	}

	public enum REL {
		SELF, LIST, FIRST, NEXT, PREV, LAST;
	}

}
