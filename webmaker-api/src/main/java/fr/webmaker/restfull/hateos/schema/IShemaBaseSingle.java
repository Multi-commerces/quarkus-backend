package fr.webmaker.restfull.hateos.schema;

import com.fasterxml.jackson.annotation.JsonProperty;


public interface IShemaBaseSingle {

	@JsonProperty("data")
	ShemaBaseData getData();
}