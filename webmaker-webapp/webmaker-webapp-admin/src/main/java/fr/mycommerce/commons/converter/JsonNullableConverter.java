package fr.mycommerce.commons.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.openapitools.jackson.nullable.JsonNullable;

@FacesConverter(value = "jsonNullableConverter", forClass = JsonNullable.class)
public class JsonNullableConverter implements Converter<JsonNullable<String>> {

	@Override
	public JsonNullable<String> getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {		
		return value == null || value.isEmpty() ? JsonNullable.undefined() : JsonNullable.of(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JsonNullable<String> value)
			throws ConverterException {
		return value.isPresent() && value.get() != null ? value.get() : null;
	}

}