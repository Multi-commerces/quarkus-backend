package fr.commerces.microservices.catalog.products.manager;

import java.util.Collection;
import java.util.Collections;
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

import fr.commerces.microservices.catalog.categories.basic.CategoryManager;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductCategory;
import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.commerces.microservices.catalog.products.relationships.lang.ProductLangManager;
import fr.commerces.microservices.catalog.products.relationships.variations.ProductVariationManager;
import fr.webmaker.annotation.ManagerInterceptor;
import fr.webmaker.data.BaseResource;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.data.product.ProductLangData;
import fr.webmaker.data.product.ProductVariationData;
import fr.webmaker.exception.crud.NotFoundDeleteException;
import fr.webmaker.exception.crud.NotFoundException;
import fr.webmaker.exception.crud.NotFoundUpdateException;
import fr.webmaker.restfull.hateos.PagingData;
import fr.webmaker.restfull.model.PageRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ManagerInterceptor
@ApplicationScoped
//@AlternativePriority(Interceptor.Priority.APPLICATION)
public final class ProductManager {

	@Inject
	ProductMapper mapper;
	
	@Inject
	ProductLangManager productLangManager;
	
	@Inject
	CategoryManager categoryManager;
	
	@Inject
	ProductVariationManager productVariationManager;
	

	/* ################ Opérations de lecture ################ */

	/**
	 * Obtenir la pagination de la liste des produits
	 * 
	 * @param languageCode
	 * @param _page
	 * @param _size
	 * @return
	 */
	public final @NotNull PagingData getPagingData(final PageRequest page) {
		
		PanacheQuery<Product> query = Product.<Product>findAll().page(Page.of(page.getPage() - 1, page.getSize()));

		final PagingData pagingData = new PagingData();
		pagingData.setNumber(page.getPage());
		pagingData.setSize(page.getSize());
		pagingData.setTotalElements(query.count());
		pagingData.setTotalPages(query.pageCount());

		return pagingData;
	}
	
	/**
	 * Obtenir tous les produits
	 * 
	 * @return
	 */
	public final @NotNull List<fr.webmaker.data.product.ProductCompositeData> findAll(@NotNull @Valid final PageRequest page,
			@NotNull final Collection<@NotNull ProductRelation> inclusions) {
		List<Product> values = Product.<Product>findAll()
				.page(Page.of(page.getPage() - 1, page.getSize())).list();
		Collection<Long> ids = values.stream()
				.map(Product::getId)
				.collect(Collectors.toList());
		
		boolean withLangs = inclusions.contains(ProductRelation.LANGS);
		boolean withCategories = inclusions.contains(ProductRelation.CATEGORIES);
		boolean withVariations = inclusions.contains(ProductRelation.VARIATIONS);
		
		// Langues
		Map<Long, List<ProductLangData>> langs = withLangs
				? productLangManager.findByProductIds(ids)
				: Map.of();
		
		// Catégories
		Map<Long, List<CategoryData>> categories = withCategories
				? categoryManager.findByProductIds(ids)
				: Map.of();
		
		// Variations
		Map<Long, List<ProductVariationData>> variations = withVariations
				? productVariationManager.findByProductIds(ids)
				: Map.of();

		// MAPPING ENTITY => DATA
		return values.stream()
				.map(entity -> {
					Long id = entity.getId();
					ProductCompositeData baseResource = mapper.wrapComposite(entity);
							baseResource.setProductLangs(
									Optional.ofNullable(langs.get(id)).orElse(Collections.emptyList()));
							baseResource.setVariations(
									Optional.ofNullable(variations.get(id)).orElse(Collections.emptyList()));
							baseResource.setCategories(
									Optional.ofNullable(categories.get(id)).orElse(Collections.emptyList()));
					return baseResource;
				})
				.collect(Collectors.toList());
	}

	/**
	 * Opération de recherche du produit par identifiant
	 * 
	 * @param productId
	 * @return
	 * @throws NotFoundException
	 */
	
	public final @NotNull ProductCompositeData findById(final long productId, final Collection<ProductRelation> relationships) throws NotFoundException {
		return Product.<Product>findByIdOptional(productId)
				.map(entity -> {
					ProductCompositeData data = mapper.wrapComposite(entity);
							if (relationships.contains(ProductRelation.CATEGORIES)) {
								data.setCategories(Optional
										.ofNullable(categoryManager.findByProductIds(List.of(productId)).get(productId))
										.orElse(Collections.emptyList()));
							}
					
					return data;
				})
				.orElseThrow(() -> new NotFoundException(productId));
	}
	
	/* ################ Opérations de création ################ */

	
	/**
	 * Opération de création d'un nouveau produit
	 * 
	 * @param data
	 * @return
	 */
	@Transactional
	public final @NotNull BaseResource createProduct(@NotNull @Valid final ProductData data) {
		final Product product = mapper.unwrap(data);
		product.persist();

		return mapper.wrapComposite(product);
	}

	/* ################ Opérations de mise à jour ################ */

	/**
	 * Opération de mise à jour d'un produit
	 * 
	 * @param productId
	 * @param data
	 * @return 
	 * @throws NotFoundException
	 */
	@Transactional
	public final @NotNull BaseResource updateProduct(long productId,
			@NotNull @Valid ProductData data) throws NotFoundException {

		return Optional.ofNullable(mapper.unwrap(data, productId)) // PATCH
				.map(mapper::wrap)
				.orElseThrow(() -> new NotFoundUpdateException(productId));
	}
	
	/**
	 * Opération de remplacement (complète) des catégories du produit
	 * @param productId produit portant la relation des catégories
	 * @param relationIdsForAdd les nouvelles catégories de la relation
	 * @return
	 * @throws NotFoundException
	 */
	@Transactional
	public final @NotNull List<@NotNull BaseResource> replaceCategories(long productId,
			@NotNull Collection<@NotNull Long> relationIdsForAdd) throws NotFoundException {

		Supplier<Stream<Category>> categories = () -> Category.findByIds(relationIdsForAdd);
		if (!categories.get().map(Category::getId).collect(Collectors.toList()).containsAll(relationIdsForAdd)) {
			throw new IllegalArgumentException("Une ou plusieurs catégories ne peuvent être ajoutées au produit");
		}
	
		// (BDD) Produit
		Product product = Product.findByIdOrElseThrow(productId);
		// (BDD) Produit - Catégories
		List<ProductCategory> productCategoryPojos = product.getCategories();
		List<Long> categoryIds = productCategoryPojos.stream()
			.map(o -> o.getCategory().getId())
			.collect(Collectors.toList());
		
		// Suppression des catégories
		productCategoryPojos.removeIf(o -> !relationIdsForAdd.contains(o.getIdentity().getCategoryId()));
		
		// Ajout des catégories non présentes pour le produit
		List<ProductCategory> toAdd = categories.get()
				.filter(o -> !categoryIds.contains(o.getId()))
			.map(category -> new ProductCategory(product, category))
			.collect(Collectors.toList());
	
		productCategoryPojos.addAll(toAdd);
		
		return Collections.emptyList();
	}
	
	/**
	 * Opération de suppression des membres de la relation catégories du produit
	 * @param productId
	 * @param memberIdsFordelete
	 * @return
	 * @throws NotFoundException
	 */
	@Transactional
	public final @NotNull long removeCategories(long productId,
			@NotNull Collection<@NotNull Long> memberIdsFordelete) throws NotFoundException {
		
		// réponse positive même si sont déjà absentes de la relation
		return ProductCategory.delete(List.of(productId), memberIdsFordelete);
	}
	

	/* ################ Opérations de suppression ################ */

	/**
	 * Opération de suppresion d'un produit ainsi que toutes les traductions de
	 * celui-ci
	 * 
	 * @param productId
	 * @throws NotFoundDeleteException
	 */
	@Transactional
	public final void deleteProduct(long productId) throws NotFoundDeleteException {
		if (!Product.deleteById(productId)) {
			throw new NotFoundDeleteException(productId);
		}
	}

}