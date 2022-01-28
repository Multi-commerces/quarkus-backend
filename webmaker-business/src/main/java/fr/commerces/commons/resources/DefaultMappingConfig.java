package fr.commerces.commons.resources;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

import fr.commerces.commons.GenericMapper;

@MapperConfig(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = GenericMapper.class)
public interface DefaultMappingConfig {
	
	
}