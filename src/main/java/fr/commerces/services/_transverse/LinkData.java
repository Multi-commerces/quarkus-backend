package fr.commerces.services._transverse;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LinkData {

	/**
	 * l’attribut rel permet de donner un sens aux URLs et de faciliter la gestion côté client
	 */
	@JsonProperty("rel")
	private String rel;

	/**
	 * Lien vers la ressource (exemple : http://localhost:port/ressource)
	 */
	@JsonProperty("href")
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
	private  HttpMethod method;

	
	public LinkData() {
		this(null, null);
	}

	public LinkData(String href) {
		this("self", href);
	}

	public LinkData(String rel, String href) {
		super();
		this.rel = rel;
		this.href = href;
		method = HttpMethod.GET;
	}

	public enum Method {
		SELF, LIST;
	}
	
	

}
