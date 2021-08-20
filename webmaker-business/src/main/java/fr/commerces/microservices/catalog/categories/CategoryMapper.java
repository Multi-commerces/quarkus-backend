package fr.commerces.microservices.catalog.categories;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.response.CategoryHierarchyResponse;

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
	public CategoryHierarchyResponse toCategoryHierarchyData(CategoryLang entity)
	{
		final CategoryHierarchyResponse categoryHierarchyData = new CategoryHierarchyResponse(toData(entity));
		categoryHierarchyData.setIdentifier(new LongID(entity.getId()));
		categoryHierarchyData.setSubCategories(subCategories(entity));
		
		return categoryHierarchyData;
	}


	private List<CategoryHierarchyResponse> subCategories(final CategoryLang entity) {
		
		final List<CategoryHierarchyResponse> subCategories = new ArrayList<>();
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
					CategoryHierarchyResponse c = toCategoryHierarchyData(rootLang);
					subCategories.add(c);
			});
		});

		return subCategories;
	}

}