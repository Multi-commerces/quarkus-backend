package fr.commerces.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Date;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class AppParamConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {

		if (rawType.equals(LocalDate.class)) {
			return (ParamConverter<T>) new LocalDateConverter();
		}

		if (rawType.equals(Date.class)) {
			return (ParamConverter<T>) new DateConverter();
		}

		return null;
	}

}