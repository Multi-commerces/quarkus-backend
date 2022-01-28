package fr.commerces.microservices.catalog.products.relationships.shipping;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.product.ProductShippingData;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ProductShippingMapper {


	/**
	 * Produit Shipping (lecture)
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductShippingData toData(Product entity);


	/**
	 * Produit Shipping (mise à jour)
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	public abstract Product toEntity(ProductShippingData data, @MappingTarget Product entity);
	




}