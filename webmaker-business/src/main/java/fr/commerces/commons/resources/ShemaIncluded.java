package fr.commerces.commons.resources;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "type", "attributes", "relationships", "links" })
public interface ShemaIncluded {

	String getId();
	
	String getType();
	
	Object getAttributes();
	
	Map<String, Object> getRelationships();
	
	ShemaLinksSelf getLinks();
	
}
