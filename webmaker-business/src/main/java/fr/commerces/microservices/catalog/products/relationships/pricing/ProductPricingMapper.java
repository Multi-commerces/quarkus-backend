package fr.commerces.microservices.catalog.products.relationships.pricing;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.product.ProductPricingData;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductPricingMapper {


	public abstract ProductPricingData wrap(Product entity);

	@Mapping(target = "id", ignore = true)
	public abstract Product unwrap(ProductPricingData data, @MappingTarget Product entity);
	
	@Mapping(target = "id", ignore = true)
	public abstract Product unwrap(ProductPricingData data, @Context Long id);

}