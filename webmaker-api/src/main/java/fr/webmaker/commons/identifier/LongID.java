package fr.webmaker.commons.identifier;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
public class LongID extends Identifier<Long> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LongID() {
		super();
	}

	public LongID(Long id) {
		super(id);
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}

	
}