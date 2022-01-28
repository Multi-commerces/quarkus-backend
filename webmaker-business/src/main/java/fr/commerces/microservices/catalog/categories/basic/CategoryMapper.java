package fr.commerces.microservices.catalog.categories.basic;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import fr.commerces.commons.resources.DefaultMappingConfig;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.commerces.microservices.catalog.categories.entity.CategoryLangPK;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangData;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangRelationData;
import fr.commerces.microservices.catalog.products.entity.ProductCategory;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryRelationData;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class CategoryMapper {
	

	/*
	 * Mapper pour opération de création
	 */
	@Mapping(target = "id", ignore = true)
	public abstract Category toEntity(CategoryData data);
	
	@Mapping(target = "id", ignore = true)
	public abstract CategoryLang toEntity(CategoryLangData data);

	/*
	 * Mapper pour opération de mise à jour
	 */
	@InheritConfiguration
	@Mapping(target = "updated", ignore = true)
	@Mapping(target = "created", ignore = true)
	public abstract CategoryLang toEntity(CategoryLangData data, @MappingTarget CategoryLang entity);

	@InheritConfiguration
	@Mapping(target = "updated", ignore = true)
	@Mapping(target = "created", ignore = true)
	public abstract Category toEntity(CategoryData data, @MappingTarget Category entity);

	/*
	 * Mapper pour opération de lecture 
	 */
	public abstract CategoryLangData toData(Category entity);
	
	@Named("toCategoryData")
	public abstract CategoryData toCategoryData(Category entity);
	public abstract List<CategoryData> toCategoryData(List<ProductCategory> entity);
	
	@Mapping(target = ".", source = "category")
	public abstract CategoryData toCategoryData(ProductCategory entity);
	
	@Mapping(target = "category", source = "category", qualifiedByName = "toCategoryHierarchyData")
	@Mapping(target = "categoryId", source = "id")
	@Mapping(target = "languageCode", source = "lang")
	@Mapping(target = "id", source = "identity", qualifiedByName = "categoryIdLang")
	public abstract CategoryLangRelationData toCompositeData(CategoryLang entity);

	@Named("categoryIdLang")
	public String categoryIdLang(CategoryLangPK pk) {
		if(pk == null)
		{
			return "null";
		}
		
		return String.join("/", String.valueOf(pk.getCategoryId()), String.valueOf(pk.getLanguageCode()));
	}
	
	@Named("toCategoryHierarchyData")
//	@Mapping(target = "category", source = ".", qualifiedByName = "toCategoryData")
	@Mapping(target = "subCategories", source = "childrenCategory")
	@Mapping(target = "parentCategory", source = "parentCategory", qualifiedByName = "toCategoryData")
	public abstract CategoryRelationData toCategoryHierarchyData(Category entity);


	
}