package fr.commerces.microservices.catalog.categories;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.exceptions.crud.NotFoundCreateException;
import fr.commerces.commons.exceptions.crud.NotFoundDeleteException;
import fr.commerces.commons.exceptions.crud.NotFoundException;
import fr.commerces.commons.exceptions.crud.NotFoundUpdateException;
import fr.commerces.commons.logged.ManagerInterceptor;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.commerces.microservices.catalog.categories.entity.CategoryLangPK;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.data.CategoryHierarchyData;

@ManagerInterceptor
@ApplicationScoped
public class CategoryManager {

	@Inject
	CategoryMapper mapper;

	/**
	 * Opération de recherche des catégories existantes
	 * 
	 * @return
	 */
	@Transactional
	public final List<CategoryHierarchyData> findCategoryHierarchy(@NotNull LanguageCode languageCode) {
		try (final Stream<CategoryLang> stream = CategoryLang.findHierarchy(languageCode).stream()) {
			return stream.map(mapper::toCategoryHierarchyData).collect(Collectors.toList());
		}
	}

	/**
	 * Opération de recherche d'une catégorie existante
	 * 
	 * @param categoryId identifiant de la catégorie
	 * @return
	 */
	public final CategoryHierarchyData findCategoryHierarchyById(@NotNull final Long categoryId, @NotNull LanguageCode languageCode) {
		final CategoryLang category = CategoryLang.<CategoryLang>findByIdOptional(new CategoryLangPK(categoryId, languageCode))
				.orElseThrow(() -> new NotFoundException(categoryId));

		return mapper.toCategoryHierarchyData(category);
	}

	/**
	 * Opération de mise à jour des informations de la catégorie
	 */
	@Transactional
	public final void update(@NotNull final Long categoryId, @NotNull final LanguageCode langueCode, @NotNull final CategoryData data)
			throws NotFoundException {
		CategoryLang.findByCategoryLangPK(categoryId, langueCode).map(pojo -> {
			pojo.getCategory().setUpdated(LocalDateTime.now(ZoneOffset.UTC));
			return mapper.toEntity(data, pojo);
		}).orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}

	/**
	 * Opération de suppression
	 */
	@Transactional
	public final void delete(@NotNull final Long categoryId) throws NotFoundException {
		if (!Category.deleteById(categoryId)) {
			throw new NotFoundDeleteException(categoryId);
		}
	}

	/**
	 * Opération de création d'une nouvelle catégorie
	 */
	@Transactional
	public final Long createCategory(final Long rootId, @NotNull final CategoryData data) throws NotFoundException {
		final CategoryLang categoryLang = mapper.toEntityLang(data);
		if (rootId != null) {
			final Category rootCategory = Category.<Category>findByIdOptional(rootId)
					.orElseThrow(() -> new NotFoundCreateException(rootId));
			categoryLang.setParentCategory(rootCategory);
		}

		categoryLang.setCategoryLangPK(LanguageCode.fr);
		categoryLang.persistAndFlush();

		return categoryLang.getId();
	}
}