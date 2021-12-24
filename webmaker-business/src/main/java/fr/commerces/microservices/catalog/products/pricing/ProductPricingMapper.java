package fr.commerces.microservices.catalog.products.pricing;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.microservices.catalog.products.entity.Product;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ProductPricingMapper {

	public abstract ProductPricingData toData(Product entity);

	public abstract Product toEntity(ProductPricingData data, @MappingTarget Product entity);

}