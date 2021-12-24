package fr.commerces.commons.mapper;

import javax.enterprise.inject.Instance;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;

/**
 * https://quarkus.io/guides/rest-json#jackson
 * @author Julien ILARI
 *
 */
public class ObjectMapperConfiguration {

  @Singleton
  ObjectMapper objectMapper(Instance<ObjectMapperCustomizer> customizers) {
    ObjectMapper mapper = new ObjectMapper();    
    
    for (ObjectMapperCustomizer customizer : customizers) {
      customizer.customize(mapper);
    }
    return mapper;
  }
}