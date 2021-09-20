package fr.commerces.microservices.catalog.products.manager;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.exceptions.crud.NotFoundDeleteException;
import fr.commerces.commons.exceptions.crud.NotFoundException;
import fr.commerces.commons.exceptions.crud.NotFoundUpdateException;
import fr.commerces.commons.logged.ManagerInterceptor;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.mapper.ProductLangMapper;
import fr.webmaker.commons.PagingData;
import fr.webmaker.microservices.catalog.products.data.ProductLangData;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ManagerInterceptor
@ApplicationScoped
//@AlternativePriority(Interceptor.Priority.APPLICATION)
public class ProductLangManager {

	/**
	 * Taille de la liste, par défaut 10 produits max
	 */
	private static final int SIZE = 10;

	@Inject
	ProductLangMapper mapper;

	/* ################ Opérations de lecture ################ */

	/**
	 * <h1>GET BY LanguageCode</h1>
	 * <p>
	 * Obtenir la pagination de la liste des produits (dans une langue)
	 * </p>
	 * 
	 * @param languageCode Code langue (obligatoire)
	 * @param optSize      taille d'une page (paramètre optionel, une valeur par
	 *                     défaut sera appliquée)
	 * @return
	 */
	public final PagingData getPagingProductLang(@NotNull final LanguageCode languageCode,
			final Optional<Integer> _page, final Optional<Integer> _size) {
		int size = _size.orElse(SIZE);
		int page = _page.orElse(1);

		final PanacheQuery<ProductLang> query = ProductLang.findByLanguageCode(languageCode).page(Page.ofSize(size));

		final PagingData pagingData = new PagingData();
		pagingData.setNumber(page);
		pagingData.setSize(size);
		pagingData.setTotalElements(query.count());
		pagingData.setTotalPages(query.pageCount());

		return pagingData;
	}

	/**
	 * <h1>GET ALL BY languageCode</h1>
	 * <p>
	 * Obtenir la liste des produits avec prise en compte des critères de recherche
	 * </p>
	 * 
	 * @param language
	 * @param page     (optionnel)
	 * @param size     (optionnel)
	 * @return liste des produits (key : identifiant du produit, value : le produit)
	 */
	public final Map<Long, ProductLangData> findAllByLanguageCode(@NotNull final LanguageCode language,
			@NotNull final Optional<@Min(1) Integer> page, 
			@NotNull final Optional<@Positive @Min(1) Integer> size
			) {
		
		return ProductLang.findByLanguageCode(language)
				.page(Page.of(page.orElse(1) - 1, size.orElse(SIZE)))
				.list()
				.stream()
				.collect(
					Collectors.toUnmodifiableMap(
							product -> product.getId(),
							product -> mapper.toProductData(product))
				);
	}
	
	public final Map<LanguageCode, ProductLangData> findAll(Long poductId) {
		return ProductLang.findByProductId(poductId)
				.list()
				.stream()
				.collect(
					Collectors.toUnmodifiableMap(
							product -> product.getLang(),
							product -> mapper.toProductData(product))
				);
	}

	/**
	 * <h1>GET BY productId AND languageCode</h1> Otenir un produit dans une langue
	 * 
	 * @param productId    identifiant
	 * @param languageCode code langue
	 * @return
	 * @throws NotFoundException Produit non trouvé
	 */
	public final ProductLangData findByProductLangPK(@NotNull final Long productId,
			@NotNull final LanguageCode languageCode) throws NotFoundException {
		final ProductLang entity = ProductLang.findByIdProductAndLanguageCode(productId, languageCode)
				.orElseThrow(() -> new NotFoundException(productId));

		return mapper.toProductData(entity);
	}

	/* ################ Opérations de mise à jour ################ */

	/**
	 * <h1>UPDATE BY productId AND languageCode</h1>
	 * <p>
	 * Opération de mise à jour d'un produit traduit dans une langue
	 * </p>
	 * 
	 * @param language  langue
	 * @param productId identifiant
	 * @param data      infos sur le produit
	 * @throws NotFoundException produit introuvable
	 */
	@Transactional
	public final void updateProductLang(@NotNull final LanguageCode language, @NotNull final Long productId,
			@NotNull @Valid final ProductLangData data) throws NotFoundException {
		/*
		 * MAJ PRODUIT
		 */
		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toProduct(data, pojo))
				.orElseThrow(() -> new NotFoundUpdateException(productId));
		/*
		 * MAJ PRODUIT LANG
		 */
		ProductLang.findByIdProductAndLanguageCode(productId, language).map(pojo -> mapper.toProductLang(data, pojo))
				.orElseThrow(() -> new NotFoundUpdateException(productId + String.valueOf(language)));
	}

	/* ################ Opérations de création ################ */

	/**
	 * <h1>CREATE BY languageCode</h1>
	 * <p>
	 * Opération de création d'un nouveau produit
	 * </p>
	 * 
	 * @param languageCode langue du produit 
	 * @param data données du produit
	 * @return
	 */
	@Transactional
	public final Long createProductLang(@NotNull final LanguageCode languageCode, @Valid final ProductLangData data) {
		final ProductLang productLang = mapper.toProductLang(data);
		productLang.setProductLangPK(languageCode);
		productLang.persistAndFlush();

		return productLang.getProduct().getId();
	}

	/* ################ Opérations de suppression ################ */

	/**
	 * Opération de suppresion d'un produit ainsi que toutes les traductions de
	 * celui-ci
	 * 
	 * @param productId identifiant du produit
	 * @throws NotFoundDeleteException
	 */
	@Transactional
	public void deleteProductLanguages(@NotNull final Long productId) throws NotFoundDeleteException {
		if (!Product.deleteById(productId)) {
			throw new NotFoundDeleteException(productId);
		}
	}

	/**
	 * Opération de suppresion d'un produit (traduit dans une langue) existant
	 * 
	 * @param languageCode code langue du produit
	 * @param productId    identifiant du produit
	 */
	@Transactional
	public void deleteProductLang(@NotNull final LanguageCode languageCode, @NotNull final Long productId) {
		if (!ProductLang.deleteByProductLangPK(productId, languageCode)) {
			throw new NotFoundDeleteException(
					String.format("productId=%s, languageCode=%s", String.valueOf(productId), languageCode.toString()));
		}
	}

}