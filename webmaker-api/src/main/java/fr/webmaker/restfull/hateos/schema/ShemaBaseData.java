package fr.webmaker.restfull.hateos.schema;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "type" })
public abstract class ShemaBaseData {

	@Schema(description = "Identifiant de la ressource", example = "1000000001", implementation = String.class)
	public abstract String getId();
	
	@Schema(description = "Type de la ressource", example = "type", implementation = String.class, required = true)
	public abstract String getType();
	
}
