package fr.commerces.services.internal.products.mapper;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services.internal.products.data.ProductData;
import fr.commerces.services.internal.products.data.ProductSeoData;
import fr.commerces.services.internal.products.data.ProductShippingData;
import fr.commerces.services.internal.products.entity.Product;
import fr.commerces.services.internal.products.entity.ProductLang;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
	public abstract ProductData toProductData(ProductLang entity);
	

	/**
	 * Produit LANGUE
	 * 
	 * @param entity
	 * @return
	 */
	public abstract Map<LanguageCode, ProductSeoData> toProductSeoDataByLang(Map<LanguageCode, ProductLang> entity);

	/**
	 * Produit SEO
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductSeoData toProductSeoResponse(ProductLang entity);
	
	/**
	 * Produit Shipping
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductShippingData toProductShippingResponse(Product entity);

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
	 * Produit Shipping
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "id", ignore = true)
	@InheritConfiguration
	public abstract Product toProduct(ProductShippingData data, @MappingTarget Product entity);

	/**
	 * Produit LANGUE
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity", ignore = true)
	@InheritConfiguration
	public abstract ProductLang toProductLang(ProductData data, @MappingTarget ProductLang entity);
	
	/**
	 * Produit SEO
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity", ignore = true)
	@InheritConfiguration
	public abstract ProductLang toProductLang(ProductSeoData data, @MappingTarget ProductLang entity);

}