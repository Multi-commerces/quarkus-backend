package fr.webmaker.commons.identifier;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class LongID extends Identifier<Long> {

	private static final long serialVersionUID = 1L;
	
	public LongID(Long id) {
		super(id);
	}


}