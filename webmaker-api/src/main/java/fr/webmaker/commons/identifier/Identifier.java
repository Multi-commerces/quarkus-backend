package fr.webmaker.commons.identifier;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Identifier<I> implements ID<I>, Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected I id;

	public Identifier() {
		super();
	}

	public Identifier(I id) {
		super();
		this.id = id;
	}

	@Override
	public I getId() {
		return id;
	}

	@Override
	public void setId(I id) {
		this.id = id;
	}

}