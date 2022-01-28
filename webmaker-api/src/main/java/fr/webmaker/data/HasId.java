package fr.webmaker.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasId<I> {

	I getId();

	@JsonIgnore
	void setId(I id);
}
