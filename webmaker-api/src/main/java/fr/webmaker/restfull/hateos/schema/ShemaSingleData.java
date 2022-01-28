package fr.webmaker.restfull.hateos.schema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.webmaker.data.BaseResource;

@JsonPropertyOrder({ "id", "type", "attributes", "relationships" })
public abstract class ShemaSingleData<T extends BaseResource> implements IShemaData<T> {

}