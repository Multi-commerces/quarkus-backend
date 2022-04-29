package fr.commerces.microservices.catalog.products.relations.stocks;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.product.ProductStockData;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductStockMapper {

	public abstract ProductStockData toData(Product entity);

	@Mapping(target = "id", ignore = true)
	public abstract Product toEntity(ProductStockData data, @MappingTarget Product entity);

}