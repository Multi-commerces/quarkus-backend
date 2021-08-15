package fr.commerces.microservices.product.categories;

import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import fr.commerces.microservices.product.entity.Category;
import fr.commerces.microservices.product.entity.CategoryLang;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class CategoryMapper {

	
	public abstract CategoryData toData(Category entity);
	public abstract Category toEntity(CategoryData data);
	
	/*
	 * Mapper pour opération de création
	 */
	@Mapping(target = "category", source = ".")
	public abstract CategoryLang toEntityLang(CategoryData data);
	
	
	/*
	 * Mapper pour opération de mise à jour
	 */
	@Mapping(target = "category", source = ".")
	public abstract CategoryLang toEntity(CategoryData data, @MappingTarget CategoryLang entity);
	@Mapping(target = "updated", ignore = true)
	@Mapping(target = "created", ignore = true)
	public abstract Category toEntity(CategoryData data, @MappingTarget Category entity);
	
	/**
	 * Mapper pour opération de lecture
	 * @param entity
	 * @return
	 */
	@Mapping(source = ".", target = "category")
	@Mapping(source = "childrenCategory", target = "subCategories", qualifiedByName = "subCategories")
	public abstract CategorySubCategoriesPairData toSubCategoriesPairData(Category entity);
	

	

	@Named("subCategories")
	Map<Long,CategorySubCategoriesPairData> subCategories(final Set<Category> categories) {
		final Map<Long,CategorySubCategoriesPairData> subCategories = new HashedMap<Long, CategorySubCategoriesPairData>();
		if(CollectionUtils.isEmpty(categories))
		{
			return subCategories;
		}
		
		categories.forEach(category -> {
			subCategories.put(category.getId(), toSubCategoriesPairData(category));
		});
		
		return subCategories;
	}

}