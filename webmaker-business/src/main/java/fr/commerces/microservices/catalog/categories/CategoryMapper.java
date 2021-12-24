package fr.commerces.microservices.catalog.categories;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.commons.mapper.JsonNullableMapper;
import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.categories.data.CategoryRelationData;
import fr.commerces.microservices.catalog.categories.data.CategoryLangData;
import fr.commerces.microservices.catalog.categories.data.CategoryLangRelationData;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.commerces.microservices.catalog.categories.entity.CategoryLangPK;

@ApplicationScoped
@Mapper(uses = JsonNullableMapper.class, config = DefaultMappingConfig.class)
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
	@Mapping(target = ".", source = ".")
	public abstract CategoryLangData toData(Category entity);
	
	@Named("toCategoryData")
	public abstract CategoryData toCategoryData(Category entity);
	
	@Mapping(target = "categoryId", source = "identity.categoryId")
	@Mapping(target = "languageCode", source = "identity.languageCode")
	@Mapping(target = "id", source = "identity", qualifiedByName = "categoryIdLang")
	public abstract CategoryLangData toData(CategoryLang entity);
	
	@Mapping(target = "categoryId", source = "identity.categoryId")
	@Mapping(target = "languageCode", source = "identity.languageCode")
	@Mapping(target = "id", source = "identity", qualifiedByName = "categoryIdLang")
	@Mapping(target = "category", source = "category", qualifiedByName = "toCategoryData")
	public abstract CategoryLangRelationData toCompositeData(CategoryLang entity);
	
	@Named("categoryIdLang")
	public String test(CategoryLangPK pk) {
		return String.join("/", String.valueOf(pk.getCategoryId()), String.valueOf(pk.getLanguageCode()));
	}
	

	@Mapping(target = "categoryLangs", source = "categoryLangs", qualifiedByName = "categoryLangs")
	@Mapping(target = "subCategories", source = "childrenCategory", qualifiedByName = "subCategories")
	public abstract CategoryRelationData toCategoryHierarchyData(Category entity);


	@Named("subCategories")
	public List<CategoryRelationData> subCategories(final Set<Category> childrenCategory) {
	
		if (CollectionUtils.isEmpty(childrenCategory)) {
			return Collections.emptyList();
		}

		return childrenCategory.stream().map(this::toCategoryHierarchyData).collect(Collectors.toList());
	}
	
	@Named("categoryLangs")
	public List<CategoryLangData> toCategoryLangs(List<CategoryLang> entity){
		return entity.stream().map(this::toData).collect(Collectors.toList());
	}

}