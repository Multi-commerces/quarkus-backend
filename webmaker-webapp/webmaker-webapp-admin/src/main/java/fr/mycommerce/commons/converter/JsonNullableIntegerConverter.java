package fr.mycommerce.commons.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.openapitools.jackson.nullable.JsonNullable;

@FacesConverter(value = "jsonNullableIntegerConverter")
public class JsonNullableIntegerConverter implements Converter<JsonNullable<Integer>> {

	@Override
	public String getAsString(FacesContext context, UIComponent component, JsonNullable<Integer> value)
			throws ConverterException {
		return value.isPresent() && value.get() != null ? String.valueOf(value.get()) : "";
	}

	@Override
	public JsonNullable<Integer> getAsObject(FacesContext context, UIComponent component, String value) {
		return value == null ? JsonNullable.undefined() : JsonNullable.of(Integer.valueOf(value));
	}

}