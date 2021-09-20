package fr.commerces.microservices.catalog.products.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;

import fr.commerces.commons.mapper.DefaultMappingConfig;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductMapper {


}