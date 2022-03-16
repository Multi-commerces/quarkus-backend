package fr.mycommerce.commons.models;

import java.io.Serializable;

import fr.webmaker.data.BaseResource;

public class Model<M extends BaseResource> implements Serializable {

	private static final long serialVersionUID = 1L;

	private M data;

	public Model() {
		super();		
	}

	public Model(final M data) {
		super();
		this.data = data;
	}

	public String getIdentifier() {
		return data.getId();
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
