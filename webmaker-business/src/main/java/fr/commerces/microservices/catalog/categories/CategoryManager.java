package fr.commerces.microservices.catalog.categories;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import fr.commerces.microservices.catalog.products.entity.ProductCategory;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.microservices.catalog.categories.data.CategoryLangData;
import fr.webmaker.microservices.catalog.categories.data.CategoryHierarchyData;

//@ManagerInterceptor
@ApplicationScoped
@Transactional
public class CategoryManager {

	@Inject
	CategoryMapper mapper;

	/**
	 * Opération de recherche des catégories existantes (recherche récursive)
	 * 
	 * @param languageCode langue de traduction cible
	 * @return
	 */
	public final List<CategoryHierarchyData> findCategoryHierarchy(@NotNull LanguageCode languageCode) {
		try (final Stream<CategoryLang> stream = CategoryLang.findHierarchy(languageCode).stream()) {
			return stream.map(mapper::toCategoryHierarchyData).collect(Collectors.toList());
		}
	}

	/**
	 * Opération de recherche d'une catégorie existante par son identifiant et la langue de traduction cible
	 * 
	 * @param categoryId identifiant de la catégorie
	 * @return l'arbre de la catégorie recherchée
	 */
	public final CategoryHierarchyData findCategoryHierarchyById(@NotNull final Long categoryId, @NotNull LanguageCode languageCode) {
		return CategoryLang.findByCategoryLangPK(categoryId, languageCode)
				.map(mapper::toCategoryHierarchyData)
				.orElseThrow(() -> new NotFoundException(categoryId));
	}
	
	/**
	 * Opération de recherche des catégories rattachées aux produits 
	 * @param productId
	 * @return
	 */
	public final Map<Long, Map<Long, CategoryLangData>> findCategoriesByProductIds(@NotNull Collection<Long> productIds) {
		
		Map<Long, Map<Long, CategoryLangData>> map = productIds.stream()
				.collect(Collectors.toMap(o -> o, o -> new HashMap<>()));

		ProductCategory.findByProductIds(productIds).stream().forEach(o -> {
			map.get(o.getIdentity().getProductId()).put(o.getIdentity().getCategoryId(), mapper.toData(o.getCategory()));
		});
		return map;

	}
	
	public final Map<Long, List<SingleCompositeData<CategoryLangData, LongID>>> findCategoriesByProductIds2(@NotNull Collection<Long> productIds) {
		final Map<Long, List<SingleCompositeData<CategoryLangData, LongID>>> values = productIds.stream()
				.collect(Collectors.toMap(o -> o, o -> new ArrayList<>()));

		ProductCategory.findByProductIds(productIds).stream().forEach(o -> {
			final SingleCompositeData<CategoryLangData, LongID> single = new SingleCompositeData<>();
			single.setData(mapper.toData(o.getCategory()));
			single.setIdentifier(new LongID(o.getIdentity().getCategoryId()));
			
			values.get(o.getIdentity().getProductId()).add(single);
		});
		return values;

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
	public final Long createCategory(final Long rootId, @NotNull final CategoryLangData data) throws NotFoundException {
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