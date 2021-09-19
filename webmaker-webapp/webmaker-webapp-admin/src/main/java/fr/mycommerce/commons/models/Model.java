package fr.mycommerce.commons.models;

import java.io.Serializable;

import fr.webmaker.commons.identifier.Identifier;

public class Model<Data extends Serializable, I extends Identifier<?>> implements Serializable {

	private static final long serialVersionUID = 1L;

	private I identifier;

	private Data data;

	public Model() {
		super();		
	}

	public Model(final I id, final Data data) {
		super();
		this.identifier = id;
		this.data = data;
	}

	public I getIdentifier() {
		return identifier;
	}

	public void setIdentifier(I Identifier) {
		this.identifier = Identifier;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
