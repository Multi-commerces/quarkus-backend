package fr.commerces.commons.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JsonNullableMapper {

	
	default String unwrap(JsonNullable<String> nullable) {
		return nullable.orElse(null);
	}

	
	default JsonNullable<String> wrap(String entity) {
		return JsonNullable.of(entity);
	}

}