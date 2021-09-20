package fr.webmaker.commons.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.identifier.Identifier;
import lombok.Data;

@Data
public class IncludedData<I extends Identifier<?>, M extends SimpleData> {
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("identifier")
	protected I identifier;
	
	@JsonProperty("attributes")
	private M attributes;

}
