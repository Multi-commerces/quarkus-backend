package fr.commerces.microservices.catalog.categories;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.categories.data.CategoryHierarchyData;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class CategoryMapper {

	/*
	 * Mapper pour opération de création
	 */

	public abstract Category toEntity(CategoryData data);

	@Mapping(target = "category", source = ".")
	public abstract CategoryLang toEntityLang(CategoryData data);

	/*
	 * Mapper pour opération de mise à jour
	 */

	public abstract CategoryData toData(Category entity);
	
	@Mapping(target = ".", source = "category")
	public abstract CategoryData toData(CategoryLang entity);

	@Mapping(target = "category", source = ".")
	public abstract CategoryLang toEntity(CategoryData data, @MappingTarget CategoryLang entity);

	@Mapping(target = "updated", ignore = true)
	@Mapping(target = "created", ignore = true)
	public abstract Category toEntity(CategoryData data, @MappingTarget Category entity);

	/*
	 * Mapper pour opération de lecture
	 */

//	@Mapping(target = "category", source = ".")
//	@Mapping(source = "category", target = "category")
//	@Mapping(target = "category.subCategories", source = "entity", qualifiedByName = "subCategories")
	public CategoryHierarchyData toCategoryHierarchyData(CategoryLang entity)
	{
		final CategoryHierarchyData categoryHierarchyData = new CategoryHierarchyData(toData(entity));
		categoryHierarchyData.setSubCategories(subCategories(entity));
		
		return categoryHierarchyData;
	}


	private Map<Long, CategoryHierarchyData> subCategories(final CategoryLang entity) {
		
		final Map<Long, CategoryHierarchyData> subCategories = new HashedMap<Long, CategoryHierarchyData>();
		if (CollectionUtils.isEmpty(entity.getChildrenCategory())) {
			return subCategories;
		}
		
		final LanguageCode lang = entity.getLang();
		
		// Pour chacune des sous-catégories
		entity.getChildrenCategory().forEach(root -> {
			// Traitement (la langue doit exister)
			root.getCategoryLang().stream()
				.filter(o -> o.getLang().equals(lang))
				.findAny()
				.ifPresent(rootLang -> {
					subCategories.put(root.getId(), toCategoryHierarchyData(rootLang));
			});
		});

		return subCategories;
	}

}