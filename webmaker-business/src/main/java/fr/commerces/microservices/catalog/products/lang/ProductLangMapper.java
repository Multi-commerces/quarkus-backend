package fr.commerces.microservices.catalog.products.lang;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.commons.mapper.JsonNullableMapper;
import fr.commerces.microservices.catalog.categories.CategoryMapper;
import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.products.ProductData;
import fr.commerces.microservices.catalog.products.ProductDepth1Data;
import fr.commerces.microservices.catalog.products.basic.ProductBasicMapper;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.pricing.ProductPricingMapper;
import fr.commerces.microservices.catalog.products.shipping.ProductShippingMapper;
import fr.commerces.microservices.catalog.products.variations.ProductVariationMapper;

@ApplicationScoped
@Mapper(uses = {JsonNullableMapper.class, 
		ProductShippingMapper.class, 
		ProductBasicMapper.class,
		ProductVariationMapper.class,
		ProductPricingMapper.class}, 
config = DefaultMappingConfig.class)
public abstract class ProductLangMapper {
	@Inject
	CategoryMapper categoryMapper;
	
	public ProductDepth1Data toDepth1(final Product source)
	{
		final ProductDepth1Data target = toDepth1Data(source);
		
		// Relation - langs
		final List<ProductLangData> langs = toList(source.getProductLang());
		
		// Relation - catégories
		final List<CategoryData> categories = source.getCategories().stream()
				.map(o -> categoryMapper.toCategoryData(o.getCategory()))
				.collect(Collectors.toList());
			target.setProductLangs(langs);
		target.setCategories(categories);	
		
		return target;
	}
	
	
	@Mapping(target = "basic", source = ".")
	@Mapping(target = "pricing", source = ".")
	@Mapping(target = "shipping", source = ".")
	@Mapping(target = "stock", source = ".")
	public abstract ProductDepth1Data toDepth1Data(Product source);
	
	@Mapping(target = "product", source = "product", qualifiedByName = "toData")
	public abstract  ProductLangDepth1Data toDepth1Data(final ProductLang source);

	
	@Named("toData")
	public abstract ProductData toData(Product source);

	/*
	 * READ Utilisation opération de lecture
	 */
	public abstract ProductLangData toProductData(ProductLang entity);
	

	public List<ProductLangData> toList(List<ProductLang> source) {
		if (source == null) {
			return null;
		}

		return source.stream().map(this::toProductData).collect(Collectors.toList());
	}

	
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
	public abstract ProductLang toProductLang(ProductLangData data);

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
	public abstract ProductLang toProductLang(ProductLangData data, @MappingTarget ProductLang entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "categories", ignore = true)
	@Mapping(target = "productLang", ignore = true)
	@Mapping(target = "variations", ignore = true)
	@Mapping(target = "images", ignore = true)
	@Mapping(source = ".", target = ".", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public abstract Product toProduct(ProductLangData data, @MappingTarget Product entity);


}