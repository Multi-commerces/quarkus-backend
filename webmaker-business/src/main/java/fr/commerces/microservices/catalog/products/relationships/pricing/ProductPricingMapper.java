package fr.commerces.microservices.catalog.products.relationships.pricing;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.product.ProductPricingData;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ProductPricingMapper {

	/**
	 * Opération de lecture
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductPricingData toData(Product entity);

	/**
	 * Opération décriture
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	public abstract Product toEntity(ProductPricingData data, @MappingTarget Product entity);

}