package fr.commerces.microservices.catalog.categories.lang;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.categories.CategoryMapper;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.webmaker.data.category.CategoryLangCompositeData;
import fr.webmaker.exception.crud.NotFoundException;

//@ManagerInterceptor
@ApplicationScoped
@Transactional
public class CategoryLangManager {

	@Inject
	CategoryMapper mapper;
	
	public final List<CategoryLangCompositeData> findCompositeByCategoryId(@NotNull Long categoryId) {
		return Category.<Category>findByIdOptional(categoryId)
				.orElseThrow(() -> new NotFoundException(categoryId))
				.getCategoryLangs().stream()
				.map(mapper::toCompositeData)
				.collect(Collectors.toList());
	}
	
	public final CategoryLangCompositeData findCompositeByCategoryIdAndLanguageCode(@NotNull Long categoryId, @NotNull LanguageCode languageCode) {
		return CategoryLang.findByCategoryLangPK(categoryId, languageCode)
				.map(mapper::toCompositeData)
				.orElseThrow(() -> new NotFoundException(categoryId));
	}

	
}