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
import fr.commerces.services.products.data.ProductSeoData;
import fr.commerces.services.products.entity.Product;
import fr.commerces.services.products.entity.ProductLang;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductMapper {

	public abstract Product toEntity(ProductData data);

	/*
	 * READ Utilisation opération de lecture
	 */

	/**
	 * Produit
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = "data", source = ".")
	public abstract GenericResponse<ProductData, Long> toResponse(Product entity);

	@Mapping(target = "id", ignore = true)
	@InheritConfiguration
	public abstract GenericResponse<ProductData, Long> toResponse(Product entity,
			@MappingTarget GenericResponse<ProductData, Long> response);

	/**
	 * Produit LANGUE
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = "data", source = ".")
	public abstract GenericResponse<ProductData, Long> toResponse(ProductLang entity);

	/**
	 * Produit SEO
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = "data", source = ".")
	public abstract GenericResponse<ProductSeoData, Long> toProductSeoResponse(ProductLang entity);

	/*
	 * CREATE Utilisation opération de création
	 */

	/**
	 * Produit LANGUE
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
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity.idProduct", ignore = true)
	@Mapping(target = "identity.language", ignore = true)
	@InheritConfiguration
	public abstract ProductLang dataIntoEntity(ProductData data, @MappingTarget ProductLang entity);
	
	/**
	 * Produit SEO
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity", ignore = true)
	@InheritConfiguration
	public abstract ProductLang dataIntoEntity(ProductSeoData data, @MappingTarget ProductLang entity);

}