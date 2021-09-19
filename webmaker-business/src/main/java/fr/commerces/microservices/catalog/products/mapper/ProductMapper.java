package fr.commerces.microservices.catalog.products.mapper;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.webmaker.microservices.catalog.products.data.ProductData;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductMapper {

	/*
	 * READ Utilisation opération de lecture
	 */
	/**
	 * Produit
	 * 
	 * @param entity
	 * @return
	 */
	public abstract Map<Long, ProductData> toProductDataById(Map<Long, ProductLang> entity);

	@Mapping(target = ".", source = "product")
	public abstract ProductData toProductData(ProductLang entity);

	
	/*
	 * CREATE Utilisation opération de création
	 */

	/**
	 * Produit LANGUE
	 * 
	 * @param data
	 * @return
	 */
	@Mapping(target = ".", source = ".")
	@Mapping(target = "product", source = ".")
	public abstract ProductLang toProductLang(ProductData data);

	/*
	 * UPDATE Utilisation opération de mise à jour
	 */


	/**
	 * Produit LANGUE
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity", ignore = true)
	@Mapping(target = "product", ignore = true)
	public abstract ProductLang toProductLang(ProductData data, @MappingTarget ProductLang entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "categories", ignore = true)
	@Mapping(target = "productLang", ignore = true)
	@Mapping(target = "variations", ignore = true)
	@Mapping(target = "images", ignore = true)
	@Mapping(source = ".", target = ".", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

	public abstract Product toProduct(ProductData data, @MappingTarget Product entity);


}