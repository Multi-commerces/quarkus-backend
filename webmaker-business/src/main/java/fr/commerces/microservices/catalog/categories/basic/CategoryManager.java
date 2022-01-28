package fr.commerces.microservices.catalog.categories.basic;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.exceptions.crud.NotFoundCreateException;
import fr.commerces.commons.exceptions.crud.NotFoundDeleteException;
import fr.commerces.commons.exceptions.crud.NotFoundException;
import fr.commerces.commons.exceptions.crud.NotFoundUpdateException;
import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.categories.data.CategoryRelationData;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangBaseData;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangData;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangRelationData;
import fr.commerces.microservices.catalog.products.entity.ProductCategory;

//@ManagerInterceptor
@ApplicationScoped
@Transactional
public class CategoryManager {

	@Inject
	CategoryMapper mapper;

	/**
	 * Opération de recherche des catégories existantes (recherche récursive)
	 * 
	 * @return
	 */
	public final List<CategoryRelationData> findCategoryHierarchy() {
		try (final Stream<Category> stream = Category.findCategoryHierarchy().stream()) {
			return stream
					.map(mapper::toCategoryHierarchyData)
					.collect(Collectors.toList());
		}
	}
	
	public final List<CategoryLangBaseData> findRelationshipsLangs(Long categoryId) {
		final List<CategoryLang> categoryLangs = Category.<Category>findByIdOptional(categoryId)
				.orElseThrow(() -> new NotFoundException(categoryId))
				.getCategoryLangs();
		if(categoryLangs == null)
		{
			return Collections.emptyList();
		}
		
		return categoryLangs.stream()
				.map(o -> new CategoryLangBaseData(o.getId(), o.getLang()))
				.collect(Collectors.toList());
	}
	
	public final CategoryLangData findCategoryLangData(@NotNull Long categoryId, @NotNull LanguageCode languageCode) {
		return CategoryLang.findByCategoryLangPK(categoryId, languageCode)
				.map(mapper::toData)
				.orElseThrow(() -> new NotFoundException(categoryId));
	}
	
	public final List<CategoryLangRelationData> findCategoryLangCompositeDataByCategoryId(@NotNull Long categoryId) {
		return Category.<Category>findByIdOptional(categoryId)
				.orElseThrow(() -> new NotFoundException(categoryId))
				.getCategoryLangs()
				.stream()
				.map(mapper::toCompositeData)
				.collect(Collectors.toList());
	}
	
	public final CategoryLangRelationData findCategoryLangCompositeData(@NotNull Long categoryId, @NotNull LanguageCode languageCode) {
		return CategoryLang.findByCategoryLangPK(categoryId, languageCode)
				.map(mapper::toCompositeData)
				.orElseThrow(() -> new NotFoundException(categoryId));
	}

	/**
	 * Opération de recherche d'une catégorie existante par son identifiant et la langue de traduction cible
	 * 
	 * @param categoryId identifiant de la catégorie
	 * @return l'arbre de la catégorie recherchée
	 */
	public final CategoryRelationData findCategoryHierarchyById(@NotNull final Long categoryId) {
		return Category.<Category>findByIdOptional(categoryId)
				.map(mapper::toCategoryHierarchyData)
				.orElseThrow(() -> new NotFoundException(categoryId));
	}
	
	/**
	 * Opération de recherche des catégories rattachées aux produits 
	 * @param productId
	 * @return
	 */
	public final Map<Long, List<CategoryRelationData>> findCategoriesByProductIds(@NotNull Collection<Long> productIds) {
		Map<Long, List<CategoryRelationData>> map = productIds.stream()
				.collect(Collectors.toMap(o -> o, o -> new ArrayList<>()));

		ProductCategory.findByProductIds(productIds).stream().forEach(o -> {
			map.get(o.getIdentity().getProductId()).add(mapper.toCategoryHierarchyData(o.getCategory()));
		});
		return map;

	}

	/**
	 * Opération de mise à jour des informations de la catégorie
	 */
	public final void update(@NotNull final Long categoryId, @NotNull final LanguageCode langueCode, @NotNull final CategoryLangData data)
			throws NotFoundException {
		CategoryLang.findByCategoryLangPK(categoryId, langueCode).map(pojo -> {
			pojo.getCategory().setUpdated(LocalDateTime.now(ZoneOffset.UTC));
			return mapper.toEntity(data, pojo);
		}).orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}
	
	public final void update(@NotNull final Long categoryId, @NotNull final CategoryData data)
			throws NotFoundException {
		Category.<Category>findByIdOptional(categoryId).map(pojo -> {
			pojo.setUpdated(LocalDateTime.now(ZoneOffset.UTC));
			return mapper.toEntity(data, pojo);
		}).orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}

	/**
	 * Opération de suppression
	 */
	public final void delete(@NotNull final Long categoryId) throws NotFoundException {
		if (!Category.deleteById(categoryId)) {
			throw new NotFoundDeleteException(categoryId);
		}
	}

	/**
	 * Opération de création d'une nouvelle catégorie
	 */
	public final Long createCategory(final Long rootId, @Valid final CategoryData data) throws NotFoundException {
		final Category categoryLang = mapper.toEntity(data);
		if (rootId != null) {
			final Category rootCategory = Category.<Category>findByIdOptional(rootId)
					.orElseThrow(() -> new NotFoundCreateException(rootId));
			categoryLang.setParentCategory(rootCategory);
		}

		
		categoryLang.persistAndFlush();

		return categoryLang.getId();
	}
}