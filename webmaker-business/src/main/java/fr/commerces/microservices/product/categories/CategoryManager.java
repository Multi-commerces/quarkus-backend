package fr.commerces.microservices.product.categories;

import java.time.LocalDateTime;
import java.util.Map;
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
import fr.commerces.microservices.product.entity.Category;
import fr.commerces.microservices.product.entity.CategoryLang;

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
	public final Map<Long, CategorySubCategoriesPairData> findCategoryHierarchy() {
		try (final Stream<Category> stream = Category.findCategoryHierarchy().stream()) {
			return mapper.subCategories(stream.collect(Collectors.toSet()));
		}
	}

	/**
	 * Opération de recherche d'une catégorie existante
	 * 
	 * @param categoryId identifiant de la catégorie
	 * @return
	 */
	public final CategorySubCategoriesPairData findById(@NotNull final Long categoryId) {
		final Category category = Category.<Category>findByIdOptional(categoryId)
				.orElseThrow(() -> new NotFoundException(categoryId));

		return mapper.toSubCategoriesPairData(category);
	}

	/**
	 * Opération de mise à jour des informations de la catégorie
	 */
	@Transactional
	public final void update(@NotNull final Long categoryId, @NotNull final LanguageCode langueCode, @NotNull final CategoryData data)
			throws NotFoundException {
		CategoryLang.findByCategoryLangPK(categoryId, langueCode).map(pojo -> {
			pojo.getCategory().setUpdated(LocalDateTime.now());
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