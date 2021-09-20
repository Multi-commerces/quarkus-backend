package fr.webmaker.commons.identifier;

import java.io.Serializable;

import lombok.Data;

@Data
public class Identifier<I> implements HasId<I>, Serializable, Comparable<Identifier<I>> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identifiant
	 */
	protected I id;

	public Identifier() {
		super();
	}

	public Identifier(I id) {
		super();
		this.id = id;
	}

	@Override
	public int compareTo(Identifier<I> o) {
		return this.id.toString().compareTo(o.id.toString());
	}

}