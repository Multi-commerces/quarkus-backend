package fr.commerces.microservices.catalog.products.relations.lang;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.commons.mapper.GenericMapper;
import fr.commerces.microservices.catalog.categories.CategoryMapper;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.entity.ProductLangPK;
import fr.commerces.microservices.catalog.products.relations.seo.ProductSeoMapper;
import fr.commerces.microservices.catalog.products.self.ProductMapper;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductLangData;

@ApplicationScoped
@Mapper(uses = { GenericMapper.class, ProductMapper.class, ProductSeoMapper.class }, config = DefaultMappingConfig.class)
public abstract class ProductLangMapper {

	@Inject
	CategoryMapper categoryMapper;

	/*
	 * READ Utilisation opération de lecture
	 */
	@Named("getProductLangId")
	public String getProductLangId(ProductLangPK pk) {
		return String.join("/", 
				String.valueOf(pk.getIdProduct() != null ? pk.getIdProduct() : ""), 
				String.valueOf(pk.getLanguage()));
	}

	
	@Mapping(target = "languageCode", source = "identity.language")
	@Mapping(target = "id", source = "identity", qualifiedByName = "getProductLangId" )
	@Mapping(target = "seo", source = ".")
	@Mapping(target = "lang", source = ".")
	public abstract ProductLangCompositeData toCompositeData(final ProductLang source);

	public List<ProductLangData> toCollectionData(List<ProductLang> source) {
		if (source == null) {
			return Collections.emptyList();
		}
		return source.stream()
				.map(this::toData)
				.collect(Collectors.toList());
	}
	
	public List<ProductLangCompositeData> toCollectionCompositeData(List<ProductLang> source) {
		if (source == null) {
			return Collections.emptyList();
		}
		return source.stream()
				.map(this::toCompositeData)
				.collect(Collectors.toList());
	}

	/**
	 * Utilisation : opération de lecture
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = "id", source = "identity", qualifiedByName = "getProductLangId")
	@Mapping(target = "languageCode", source = "identity.language")
	public abstract ProductLangData toData(ProductLang entity);

	/**
	 * Utilisation : opération de création
	 * 
	 * @param data
	 * @return
	 */
	public abstract ProductLang toEntity(ProductLangData data);

	/**
	 * Utilisation : opération de mise à jour
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	public abstract ProductLang toExistingEntity(ProductLangData data, @MappingTarget ProductLang entity);

}