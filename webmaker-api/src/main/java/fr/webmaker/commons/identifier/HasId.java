package fr.webmaker.commons.identifier;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasId<I> {

	
	@JsonIgnore
	I getId();

	void setId(I id);
}
