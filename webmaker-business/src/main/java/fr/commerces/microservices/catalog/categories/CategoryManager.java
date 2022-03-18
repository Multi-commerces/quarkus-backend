package fr.commerces.microservices.catalog.categories;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.commerces.microservices.catalog.products.entity.ProductCategory;
import fr.webmaker.data.BaseResource;
import fr.webmaker.data.category.CategoryCompositeData;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryLangData;
import fr.webmaker.exception.crud.NotFoundCreateException;
import fr.webmaker.exception.crud.NotFoundDeleteException;
import fr.webmaker.exception.crud.NotFoundException;
import fr.webmaker.exception.crud.NotFoundUpdateException;

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
	public final List<CategoryCompositeData> findCategoryHierarchy() {
		Supplier<Stream<Category>> stream = () -> Category.findCategoryHierarchy().stream();
		return stream.get()
				.map(mapper::toCategoryHierarchyData)
				.collect(Collectors.toList());
		
	}

	/**
	 * Opération de recherche d'une catégorie existante par son identifiant et la langue de traduction cible
	 * 
	 * @param categoryId identifiant de la catégorie
	 * @return l'arbre de la catégorie recherchée
	 */
	public final CategoryCompositeData findCategoryHierarchyById(@NotNull final Long categoryId) {
		return Category.<Category>findByIdOptional(categoryId)
				.map(mapper::toCategoryHierarchyData)
				.orElseThrow(() -> new NotFoundException(categoryId));
	}
	
	/**
	 * Opération de recherche de catégories
	 * @param productIds
	 * @return  les catégories par produit
	 */
	public Map<Long, List<CategoryData>> findByProductIds(@NotNull final Collection<Long> productIds) {
		try (final Stream<ProductCategory> streamEntity = ProductCategory.findByProductIds(productIds).stream()) {
			return streamEntity
					.collect(Collectors.groupingBy(o -> o.getProduct().getId(),
							Collectors.mapping(mapper::toCategoryData, Collectors.toList())));
		}
	}

	/**
	 * Opération de mise à jour des informations de la catégorie
	 */
	public final void update(@NotNull final Long categoryId, @NotNull final LanguageCode langueCode, 
			@NotNull final CategoryLangData data)
			throws NotFoundException {
		CategoryLang.findByCategoryLangPK(categoryId, langueCode).map(pojo -> {
			pojo.getCategory().setUpdated(LocalDateTime.now(ZoneOffset.UTC));
			return mapper.toEntity(data, pojo);
		}).orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}
	
	
	/**
	 * Opération de mise à jour d'une catégorie avec références enfants et référence parent
	 * @param categoryId
	 * @param data
	 * @param parent
	 * @param children
	 * @return
	 * @throws NotFoundException
	 */
	public final CategoryCompositeData update(long categoryId, @NotNull final CategoryData data)
			throws NotFoundException {
		return Category.<Category>findByIdOptional(categoryId)
			.map(pojo -> {
				Category category = mapper.toEntity(data, pojo);
				category.setUpdated(LocalDateTime.now(ZoneOffset.UTC));
				
				return mapper.toCategoryHierarchyData(category);
			})
			.orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}
	
	public final CategoryCompositeData replaceChilden(long categoryId, @NotNull final  Collection<@NotNull ? extends BaseResource> children)
			throws NotFoundException {

		return Category.<Category>findByIdOptional(categoryId)
			.map(category -> {
				category.setUpdated(LocalDateTime.now(ZoneOffset.UTC));
			
				// UPDATE CHILDREN
				if (!children.isEmpty()) {
					var childrenCategories = Category.findByIds(children.stream()
							.map(o -> Long.valueOf(o.getId()))
							.collect(Collectors.toList()))
						.collect(Collectors.toList());
					
					if(childrenCategories.size() != children.size())
					{
						// Créer une custom exception
						throw new IllegalArgumentException("children error");
					}
					
					
					childrenCategories.stream()
						.forEach(o -> o.setParentCategory(category));
						
					category.setChildrenCategory(childrenCategories);
				}
				
				return mapper.toCategoryHierarchyData(category);
			})
			.orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}
	
	public final CategoryCompositeData replaceParent(final long categoryId,  final Optional<BaseResource> parent)
			throws NotFoundException {

		return Category.<Category>findByIdOptional(categoryId)
			.map(category -> {
				category.setUpdated(LocalDateTime.now(ZoneOffset.UTC));
				// UPDATE PARENT
				if (parent.isPresent()) {
					var parentCategory = Category.<Category>findByIdOptional(parent.get().getId())
							.orElseThrow(() -> new NotFoundException(parent.get().getId()));
					if (!category.getParentCategory().equals(parentCategory)) {
						parentCategory.getChildrenCategory().add(category);
						category.setParentCategory(parentCategory);
					}
				} else {
					var parentCategory = category.getParentCategory();
					if (parentCategory != null) {
						parentCategory.getChildrenCategory().removeIf(c -> c.getId().equals(category.getId()));
					}
					category.setParentCategory(null);
				}
				
				return mapper.toCategoryHierarchyData(category);
			})
			.orElseThrow(() -> new NotFoundUpdateException(categoryId));
	}
	
	/**
	 * Opération de suppression d'une catégorie existante
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
		categoryLang.persist();

		return categoryLang.getId();
	}
}