package fr.webmaker.mapper;

import java.util.Optional;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OptionalMapper {

	
	default String unwrap(Optional<String> nullable) {
		return nullable.orElse(null);
	}

	
	default Optional<String> wrap(String entity) {
		return Optional.of(entity);
	}

}