package fr.mycommerce.commons.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.openapitools.jackson.nullable.JsonNullable;



@FacesConverter(forClass = JsonNullable.class)
public class JsonNullableConverter implements Converter<JsonNullable<?>> {

	@Override
	public JsonNullable<?> getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JsonNullable<?> value)
			throws ConverterException {
		// TODO Auto-generated method stub
		return  value.isPresent() ? String.valueOf(value.get()) : null;
	}



	

  
}