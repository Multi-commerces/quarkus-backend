package fr.commerces.commons.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@MapperConfig(componentModel = "cdi", 
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
injectionStrategy = InjectionStrategy.CONSTRUCTOR, 
nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface DefaultMappingConfig {
}