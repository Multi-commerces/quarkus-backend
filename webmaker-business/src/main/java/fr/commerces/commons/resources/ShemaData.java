package fr.commerces.commons.resources;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "type", "attributes", "relationships" })
public interface ShemaData<T> {

	public String getId();
	
	public String getType();
	
	public T getAttributes();
	
	public ShemaRelationships getRelationships();
	
	
}
