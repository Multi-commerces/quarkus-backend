package fr.commerces.microservices.catalog.products.relations.seo;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.commons.mapper.GenericMapper;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.entity.ProductLangPK;
import fr.commerces.microservices.catalog.products.self.ProductMapper;
import fr.webmaker.data.product.ProductSeoData;

@ApplicationScoped
@Mapper(uses = { GenericMapper.class },config = DefaultMappingConfig.class)
public abstract class ProductSeoMapper {

	@Named("getProductSeoId")
	public String getProductSeoId(ProductLangPK pk) {
		return String.join("/", 
				String.valueOf(pk.getIdProduct() != null ? pk.getIdProduct() : ""),
				String.valueOf(pk.getLanguage()));
	}
	
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
	@Mapping(target = "languageCode", source = "identity.language")
	@Mapping(target = "id", source = "identity", qualifiedByName = "getProductSeoId")
	public abstract ProductSeoData toData(ProductLang entity);

	/**
	 * Produit SEO
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "identity", ignore = true)
	@Mapping(target = "product", ignore = true)
	public abstract ProductLang toEntity(ProductSeoData data, @MappingTarget ProductLang entity);

}