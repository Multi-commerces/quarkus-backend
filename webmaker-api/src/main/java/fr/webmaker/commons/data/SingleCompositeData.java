package fr.webmaker.commons.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Link;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.data.LinkData;
import fr.webmaker.commons.data.RelationData;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Réprésente une seul occurence de réponse
 * 
 * @author Julien ILARI
 *
 * @param <M> Data
 * @param <I> Identifier
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SingleResponse<M, I extends Identifier<?>>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String JSON_NODE_IDENTIFIER = "identifier";
	public static final String JSON_NODE_ATTRIBUTES = "attributes";
	public static final String JSON_NODE_ACTIONS = "_actions";
	public static final String JSON_NODE_RELATIONS = "_relationships";
	public static final String JSON_NODE_LINKS = "_links";
	
	private String type;

	/**
	 * Identifiant
	 */
	@JsonProperty(JSON_NODE_IDENTIFIER)
	protected I identifier;

	/**
	 * <h1>objet d'attributs</h1> Représentant certaines des données de la
	 * ressource.
	 * 
	 * <p>
	 * les clés étrangères NE DEVRAIENT PAS apparaître comme des attributs.
	 * </p>
	 */
	@JsonProperty(JSON_NODE_ATTRIBUTES)
	protected M data;

	@JsonProperty(JSON_NODE_ACTIONS)
	protected List<Link> actions = new ArrayList<>();
	
	/**
	 * Objet de relations
	 */
	@JsonProperty(JSON_NODE_RELATIONS)
	private Map<String, RelationData> relationships = new HashMap<>();
	
	
	/**
	 * <h1>Liens de ressources</h1>
	 * 
	 * <p>
	 * Le principe HATEOAS introduit tout simplement des transitions possibles entre
	 * les différents états d’une même ressource, ainsi qu’entre les ressources
	 * elles-mêmes.
	 * </p>
	 *
	 * <p>
	 * Le links membre facultatif dans chaque objet de ressource contient des liens
	 * liés à la ressource.
	 * 
	 * S'il est présent, cet objet de liens PEUT contenir un self lien qui identifie
	 * la ressource représentée par l'objet de ressource.
	 * </p>
	 */
	@JsonProperty(JSON_NODE_LINKS)
	private List<LinkData> links = new ArrayList<>();


	public SingleResponse() {
		this(null, null);
	}
	
	public SingleResponse(String type, I id, M data) {
		super();
		this.identifier = id;
		this.data = data;
		this.type = type;
	}

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param data
	 */
	public SingleResponse(I id, M data) {
		this(null, id, data);
	}

}
