package fr.mycommerce.commons.models;

import java.io.Serializable;

import fr.webmaker.data.BaseResource;

/**
 * Model d'encapsulation du POJO Data (extends BaseResource), réponse aux différents services API (RestFul JSON::API).
 * @author Julien ILARI
 *
 * @param <M>
 */
public class Model<M extends BaseResource> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * La ressource échangée entre la partie WEB et METIER (Web-API RestFul)
	 */
	private M data;

	public Model() {
		super();		
	}

	public Model(final M data) {
		super();
		this.data = data;
	}

	public String getIdentifier() {
		return data != null ? data.getId() : null;
	}
	
	public String getId() {
		return data != null ? data.getId() : null;
	}

	public void setIdentifier(String Identifier) {
		data.setId(Identifier);
	}

	public M getData() {
		return data;
	}

	public void setData(M data) {
		this.data = data;
	}

}
