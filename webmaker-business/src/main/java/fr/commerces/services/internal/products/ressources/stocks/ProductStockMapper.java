package fr.commerces.services.internal.products.ressources.stocks;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.services.internal.products.entity.Product;
import fr.webmaker.product.data.ProductStockData;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ProductStockMapper {

	/*
	 * READ Utilisation opération de lecture
	 */

	/**
	 * Produit Stock
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductStockData toData(Product entity);

	/*
	 * UPDATE Utilisation opération de mise à jour
	 */

	/**
	 * Produit Stock
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	public abstract Product toEntity(ProductStockData data, @MappingTarget Product entity);

}