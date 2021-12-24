package fr.commerces.commons.mapper;

import javax.inject.Singleton;

import org.openapitools.jackson.nullable.JsonNullableModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class RegisterCustomModuleCustomizer implements ObjectMapperCustomizer {

	public void customize(ObjectMapper mapper) {
		 mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		 mapper.registerModule(new JsonNullableModule());
	}
}