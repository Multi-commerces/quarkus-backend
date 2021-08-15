package fr.webmaker.commons.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.webmaker.commons.LinkData;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;

/**
 * Réprésente une seul occurence de réponse
 * @author Julien ILARI
 *
 * @param <M> Data
 * @param <I> Identifier
 */
@Getter
@Setter
public class SingleResponse<M, I extends Identifier<?>> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant
	 */
	protected I identifier;


	/**
	 * Données
	 */
	protected M data;

	/**
	 * Le principe HATEOAS introduit tout simplement des transitions possibles entre
	 * les différents états d’une même ressource, ainsi qu’entre les ressources
	 * elles-mêmes.
	 */
	protected List<LinkData> _links = new ArrayList<>();
	
	/**
	 * Constructeur par défaut
	 */
	public SingleResponse() {
		super();
	}

	/**
	 * Constructeur
	 * @param id
	 * @param data
	 */
	public SingleResponse(I id, M data) {
		super();
		this.identifier = id;
		this.data = data;
	}
	
	

}
