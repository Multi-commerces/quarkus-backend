package fr.commerces.commons.resources;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "id" })
public interface ShemaRelation {

	String getId();
	
	String getType();
	
}
