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

import org.apache.commons.lang3.RandomStringUtils;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductCategory;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.commerces.microservices.catalog.products.relationships.lang.ProductLangManager;
import fr.commerces.microservices.catalog.products.relationships.lang.ProductLangMapper;
import fr.commerces.microservices.catalog.products.relationships.pricing.ProductPricingMapper;
import fr.commerces.microservices.catalog.products.relationships.shipping.ProductShippingMapper;
import fr.commerces.microservices.catalog.products.relationships.stocks.ProductStockMapper;
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
	
	@Inject
	ProductPricingMapper pricingMapper;
	
	@Inject
	ProductShippingMapper shippingMapper;
	
	@Inject
	ProductStockMapper stockMapper;
	
	@Inject
	ProductLangMapper langMapper;
	

	

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
	
	
	private @NotNull List<ProductCompositeData> wrap(Collection<Product> values, @NotNull final Collection<@NotNull ProductRelation> inclusions)
	{
		final Collection<Long> ids = values.stream()
				.map(Product::getId)
				.collect(Collectors.toList());
		
		// Langues
		boolean withLangs = inclusions.contains(ProductRelation.LANGS);
		Map<Long, List<ProductLangData>> langs = withLangs
				? productLangManager.findByProductIds(ids)
				: Map.of();
		
		// Catégories
		boolean withCategories = inclusions.contains(ProductRelation.CATEGORIES);
		Map<Long, List<CategoryData>> categories = withCategories
				? categoryManager.findByProductIds(ids)
				: Map.of();
		
		// Variations
		boolean withVariations = inclusions.contains(ProductRelation.VARIATIONS);
		Map<Long, List<ProductVariationData>> variations = withVariations
				? productVariationManager.findByProductIds(ids)
				: Map.of();
				
		boolean withPricins = inclusions.contains(ProductRelation.PRICING);
		boolean withShipping = inclusions.contains(ProductRelation.SHIPPING);

		// MAPPING ENTITY => DATA
		return values.stream()
				.map(entity -> {
					Long id = entity.getId();
					ProductCompositeData baseResource = mapper.wrapComposite(entity);
					baseResource.setProductLangs(
							withLangs 
								? Optional.ofNullable(langs.get(id)).orElse(Collections.emptyList())
								: null);
					
					baseResource.setVariations(
							withVariations
							? Optional.ofNullable(variations.get(id)).orElse(Collections.emptyList())
							: null);
				
					baseResource.setCategories(
							withCategories
							? Optional.ofNullable(categories.get(id)).orElse(Collections.emptyList())
							: null);
					
					
					baseResource.setImageCover(null);
					

					baseResource.setPricing(withPricins ? pricingMapper.wrap(entity) : null);
					baseResource.setShipping(withShipping ? shippingMapper.toData(entity) : null);
					
					return baseResource;
				})
				.collect(Collectors.toList());
	}
	
	/**
	 * Obtenir tous les produits
	 * 
	 * @return
	 */
	@NotNull
	public final List<@NotNull ProductCompositeData> findAll(@NotNull @Valid final PageRequest page,
			@NotNull final Collection<@NotNull ProductRelation> inclusions) {
		
		final List<Product> values = Product.<Product>findAll().page(Page.of(page.getPage() - 1, page.getSize())).list();
		if(values.isEmpty())
		{
			return Collections.emptyList();
		}
		
		return wrap(values, inclusions);
		
	}

	/**
	 * Opération de recherche du produit par identifiant
	 * 
	 * @param productId
	 * @return
	 * @throws NotFoundException
	 */
	public final @NotNull ProductCompositeData findById(final long productId, @NotNull final Collection<@NotNull ProductRelation> relationships) {
		Product product = Product.<Product>findByIdOptional(productId)
			.orElseThrow(() -> new NotFoundException(productId));
		
		return wrap(List.of(product), relationships).get(0);
	}
	
	/* ################ Opérations de création ################ */

	
	/**
	 * Opération de création d'un nouveau produit
	 * 
	 * @param data
	 * @return
	 */
	@Transactional
	public final @NotNull BaseResource createProduct(@NotNull @Valid final ProductCompositeData data) {
		Product product = mapper.unwrap(data);	
		if(product.getReference() == null)
		{
			product.setReference(RandomStringUtils.random(10, true, false));
		}
		
		// ProductLangs
		if(data.getProductLangs().isEmpty())
		{
			ProductLang defaultLang = new ProductLang();
			defaultLang.setProductLangPK(product.getId(), LanguageCode.fr);
			defaultLang.setName("undefined");
			defaultLang.setDescription("undefined");
			defaultLang.setSummary("undefined");
			defaultLang.setProduct(product);
			product.getProductLangs().add(defaultLang);
		}
		else
		{
			data.getProductLangs().stream()
			.map(langMapper::toEntity)
			.forEach(o -> {
				o.setProductLangPK(null, LanguageCode.fr);
				o.setProduct(product);
				product.getProductLangs().add(o);
			});
		}
		
		pricingMapper.unwrap(data.getPricing(), product);
		stockMapper.toEntity(data.getStock(), product);
		

		// PERSIST PRODUCT
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