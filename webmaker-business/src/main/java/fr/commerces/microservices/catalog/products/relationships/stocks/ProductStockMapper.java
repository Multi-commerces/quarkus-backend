package fr.commerces.microservices.catalog.products.relationships.stocks;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.product.ProductStockData;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, config = DefaultMappingConfig.class, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ProductStockMapper {

	public abstract ProductStockData toData(Product entity);

	@Mapping(target = "id", ignore = true)
	public abstract Product toEntity(ProductStockData data, @MappingTarget Product entity);

}