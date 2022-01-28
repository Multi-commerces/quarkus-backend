package fr.webmaker.restfull.hateos.schema;

import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.webmaker.data.BaseResource;

@JsonPropertyOrder({ "id", "type", "attributes", "relationships" })
public abstract class ShemaCollectionData<T extends BaseResource> extends ShemaBaseData {

	@Schema(description = "Un objet d'attributs représentant certaines des données de la ressource.", required = true)
	@JsonProperty("attributes")
	public abstract T getAttributes();

	@Schema(description = "Objet de relations représentent des références de l' objet de ressource", implementation = Object.class)
	public abstract Map<String, Object> getRelationships();
}
