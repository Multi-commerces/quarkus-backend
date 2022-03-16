package fr.commerces.commons;

import javax.inject.Singleton;

import org.openapitools.jackson.nullable.JsonNullableModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class MyObjectMapperCustomizer implements ObjectMapperCustomizer {

	@Override
	public void customize(ObjectMapper objectMapper) {
		// Valeur qui indique que seules les propriétés avec des valeurs non nulles
		// doivent être incluses.
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JsonNullableModule());
	}
}