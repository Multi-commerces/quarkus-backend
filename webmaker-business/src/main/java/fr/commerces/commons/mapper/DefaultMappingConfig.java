package fr.commerces.commons.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = GenericMapper.class)
public interface DefaultMappingConfig {
	
	
}