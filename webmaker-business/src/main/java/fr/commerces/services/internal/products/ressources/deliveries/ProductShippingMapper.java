package fr.commerces.services.internal.products.ressources.deliveries;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.services.internal.products.entity.Product;
import fr.webmaker.product.data.ProductShippingData;

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