package fr.webmaker.restfull.hateos.schema;

import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.webmaker.data.BaseResource;

@JsonPropertyOrder({ "id", "type", "attributes", "relationships" })
public interface IShemaData<T extends BaseResource> {

	@Schema(description = "Identifiant de la ressource", example = "10000001", implementation = String.class)
	public String getId();
	
	@Schema(description = "Type de la ressource", example = "type", implementation = String.class, required = true)
	public String getType();
	
	@Schema(description = "Un objet d'attributs représentant certaines des données de la ressource.", required = true)
	@JsonProperty("attributes")
	public T getAttributes();
	
	@Schema(description = "Objet de relations représentent des références de l' objet de ressource", implementation = Object.class)
	Map<String, Object> getRelationships();
	
}
