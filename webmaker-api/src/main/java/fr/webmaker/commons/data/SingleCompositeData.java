package fr.webmaker.commons.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.identifier.Identifier;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SingleCompositeData<M, I extends Identifier<?>>  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String JSON_NODE_IDENTIFIER = "identifier";
	public static final String JSON_NODE_ATTRIBUTES = "attributes";
	public static final String JSON_NODE_RELATIONS = "_relationships";
	public static final String JSON_NODE_LINKS = "_links";

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
	
	/**
	 * Objet de relations
	 */
	@JsonProperty(JSON_NODE_RELATIONS)
	protected Map<String, RelationData> relationships;
	
	
	/**
	 * <h1>Liens de ressources</h1>
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
	protected List<LinkData> links;


	@Generated("SparkTools")
	private SingleCompositeData(Builder<M,I> builder) {
		this.identifier = builder.identifier;
		this.data = builder.data;
		this.relationships = builder.relationships;
		this.links = builder.links;
	}


	public SingleCompositeData() {
		this(null, null);
	}
	
	public SingleCompositeData(String type, I id, M data) {
		super();
		this.relationships = new HashMap<>();
		this.links = new ArrayList<>();
		this.identifier = id;
		this.data = data;
	}

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param data
	 */
	public SingleCompositeData(I id, M data) {
		this(null, id, data);
	}

	/**
	 * Creates builder to build {@link SingleCompositeData}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static <M, I extends Identifier<?>>Builder<M, I> builder() {
		return new Builder<M, I>();
	}

	/**
	 * Builder to build {@link SingleCompositeData}.
	 */
	@Generated("SparkTools")
	public static final class Builder<M, I extends Identifier<?>> {
		private I identifier;
		private M data;
		private Map<String, RelationData> relationships = Collections.emptyMap();
		private List<LinkData> links = Collections.emptyList();

		private Builder() {
		}

		public Builder<M,I> identifier(I identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder<M,I> data(M data) {
			this.data = data;
			return this;
		}

		public Builder<M,I> relationships(Map<String, RelationData> relationships) {
			this.relationships = relationships;
			return this;
		}

		public Builder<M,I> links(List<LinkData> links) {
			this.links = links;
			return this;
		}

		public SingleCompositeData<M,I> build() {
			return new SingleCompositeData<M,I>(this);
		}
	}
	
	

}
