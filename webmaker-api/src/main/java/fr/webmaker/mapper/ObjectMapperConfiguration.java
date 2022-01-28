package fr.webmaker.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;

import org.openapitools.jackson.nullable.JsonNullableModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;

/**
 * https://quarkus.io/guides/rest-json#jackson
 * 
 * @author Julien ILARI
 *
 */
@ApplicationScoped
public class ObjectMapperConfiguration {

	@Produces
	public final ObjectMapper objectMapper(Instance<ObjectMapperCustomizer> customizers) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JsonNullableModule());
		
		for (ObjectMapperCustomizer customizer : customizers) {
			customizer.customize(objectMapper);
		}

		return objectMapper;
	}
}