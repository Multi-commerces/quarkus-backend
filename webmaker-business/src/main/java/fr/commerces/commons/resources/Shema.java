package fr.commerces.commons.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "data", "included" })
public interface Shema {

	List<ShemaData<?>> getIncluded();

}
