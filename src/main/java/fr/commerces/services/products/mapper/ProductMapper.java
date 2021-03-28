package fr.commerces.services.products.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductData;
import fr.commerces.services.products.entity.Product;
import fr.commerces.services.products.entity.ProductLang;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductMapper {

	public abstract Product toEntity(ProductData data);

	/*
	 * READ
	 */
	
	/**
	 * Utilisation opération de lecture de Product
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = "id", source = "id")
	@Mapping(target = "data", source = ".")
	public abstract GenericResponse<ProductData, Long> toResponse(Product entity);
	
	@Mapping(target = "id", ignore = true)
	@InheritConfiguration
	public abstract GenericResponse<ProductData, Long> toResponse(Product entity, @MappingTarget GenericResponse<ProductData, Long> response);

	/**
	 * Utilisation opération de lecture de ProductLang
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = "data", source = "entity")
	public abstract GenericResponse<ProductData, Long> toResponse(ProductLang entity);
	
	/*
	 * CREATE
	 */

	/**
	 * Utilisation création d'un nouveau ProductLang
	 * 
	 * @param data
	 * @return
	 */
	@Mapping(target = ".", source = ".")
	@Mapping(target = "product", source = ".")
	public abstract ProductLang toProductLang(ProductData data);

	/*
	 * UPDATE
	 */
	
	/**
	 * Utilisation mise à jour ProductLang
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity.idProduct", ignore = true)
	@Mapping(target = "identity.language", ignore = true)
	@InheritConfiguration
	public abstract ProductLang dataIntoEntity(ProductData data, @MappingTarget ProductLang entity);

}