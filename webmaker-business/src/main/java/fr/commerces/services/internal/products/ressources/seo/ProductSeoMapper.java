package fr.commerces.services.internal.products.ressources.seo;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services.internal.products.entity.ProductLang;
import fr.webmaker.product.data.ProductSeoData;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ProductSeoMapper {

	/*
	 * READ Utilisation opération de lecture
	 */


	/**
	 * Produit SEO
	 * 
	 * @param entity
	 * @return
	 */
	public abstract Map<LanguageCode, ProductSeoData> toMap(Map<LanguageCode, ProductLang> entity);

	/**
	 * Produit SEO
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductSeoData toData(ProductLang entity);

	/**
	 * Produit SEO
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(source = ".", target = ".", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "identity", ignore = true)
	@Mapping(target = "product", ignore = true)
	@InheritConfiguration
	public abstract ProductLang toEntity(ProductSeoData data, @MappingTarget ProductLang entity);

}