package fr.webmaker.restfull.hateos.schema;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IShemaBaseCollection {

	@JsonProperty("data")
	public ShemaBaseData[] getData();

}