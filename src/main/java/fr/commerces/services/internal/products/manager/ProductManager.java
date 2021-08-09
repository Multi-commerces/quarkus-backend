package fr.commerces.services.internal.products.manager;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.NotFoundException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.logged.Logged;
import fr.commerces.services._transverse.data.PagingData;
import fr.commerces.services.internal.products.data.ProductData;
import fr.commerces.services.internal.products.data.ProductSeoData;
import fr.commerces.services.internal.products.data.ProductShippingData;
import fr.commerces.services.internal.products.entity.Product;
import fr.commerces.services.internal.products.entity.ProductLang;
import fr.commerces.services.internal.products.mapper.ProductMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@Logged
@ApplicationScoped
//@AlternativePriority(Interceptor.Priority.APPLICATION)
public class ProductManager {

	/**
	 * Taille de la liste, par défaut 10 produits max
	 */
	private static final int SIZE = 10;

	@Inject
	ProductMapper mapper;

	/* ################ Opérations de lecture ################ */

	/**
	 * Obtenir la pagination pour
	 * 
	 * @param language
	 * @param optSize
	 * @return
	 */
	public final PagingData getPagingProductLang(final LanguageCode language, final Optional<Integer> optSize) {
		int size = optSize.orElse(SIZE);

		final PanacheQuery<ProductLang> query = ProductLang.findByLanguageCode(language).page(Page.ofSize(size));

		final PagingData pagingData = new PagingData();
		pagingData.setPage(1);
		pagingData.setPageSize(size);
		pagingData.setTotalItems(query.count());
		pagingData.setTotalPages(query.pageCount());

		return pagingData;
	}

	/**
	 * Obtenir la liste des produits avec prise en compte des critères de recherche
	 * 
	 * @param language
	 * @param page     (optionnel)
	 * @param size     (optionnel)
	 * @return liste des produits (key : identifiant du produit, value : le produit)
	 */
	public final Map<Long, ProductData> findAllByLanguageCode(@NotNull final LanguageCode language,
			final Optional<@Min(1) Integer> optPage, final Optional<@Positive @Min(1) Integer> size) {

		var page = Page.of(optPage.orElse(1) - 1, size.orElse(SIZE));
		try (final Stream<ProductLang> streamEntity = ProductLang.findByLanguageCode(language).page(page).stream()) {
			var map = streamEntity.collect(Collectors.toMap(ProductLang::getId, Function.identity()));
			return mapper.toProductDataById(map);
		}
	}

	/**
	 * Obtenir les informations d'expédition du produit
	 * 
	 * @param productId
	 * @return
	 */
	public final ProductShippingData findShippingByProductId(@NotNull final Long productId) {
		final Product product = Product.<Product>findByIdOptional(productId).orElseThrow(NotFoundException::new);
		return mapper.toProductShippingResponse(product);
	}

	/**
	 * Otenir les informations SEO du produit (toutes les langues)
	 * 
	 * @param productId identifiant du produit
	 * @param language  langue du produit
	 * @return
	 */
	public final Map<LanguageCode, ProductSeoData> findSeoByProduct(@NotNull final Long productId) {
		try (Stream<ProductLang> streamEntity = Product.<Product>findByIdOptional(productId)
				.orElseThrow(NotFoundException::new).getProductLang().stream()) {
			return mapper.toProductSeoDataByLang(
					streamEntity.collect(Collectors.toMap(ProductLang::getLang, Function.identity())));
		}
	}

	/**
	 * Obtenir les information SEO du produit
	 * 
	 * @param productId identifiant du produit
	 * @param language  langue du produit
	 * @return
	 */
	public final ProductSeoData findSeoByProductLangPK(@NotNull final Long productId,
			@NotNull final LanguageCode languageCode) {
		final ProductLang pojo = ProductLang.findByIdProductAndLanguageCode(productId, languageCode)
				.orElseThrow(NotFoundException::new);

		return mapper.toProductSeoResponse(pojo);
	}

	public final ProductData findByProductLangPK(@NotNull final Long productId,
			@NotNull final LanguageCode languageCode) {
		final ProductLang entity = ProductLang.findByIdProductAndLanguageCode(productId, languageCode)
				.orElseThrow(NotFoundException::new);

		return mapper.toProductData(entity);
	}

	/* ################Opérations de mise à jour ################ */

	/**
	 * Opération de mise à jour d'un produit traduit dans une langue
	 * 
	 * @param language
	 * @param productId
	 * @param data
	 */
	@Transactional
	public final void updateProductLang(@NotNull final LanguageCode language, @NotNull final Long productId,
			@Valid final ProductData data) {
		ProductLang.findByIdProductAndLanguageCode(productId, language).map(pojo -> mapper.toProductLang(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

	/**
	 * Opération de mise à jour Produit SEO
	 * 
	 * @param languageCode langue du produit
	 * @param productId    identifiant du produit
	 * @param data
	 */
	@Transactional
	public final void updateSEO(@NotNull final LanguageCode languageCode, @NotNull final Long productId,
			@Valid final ProductSeoData data) {
		ProductLang.findByIdProductAndLanguageCode(productId, languageCode)
				.map(pojo -> mapper.toProductLang(data, pojo)).orElseThrow(NotFoundException::new);
	}

	/**
	 * Opération de mise à jour des informations d'expédition du produit
	 * 
	 * @param productId
	 * @param data
	 */
	@Transactional
	public final void updateShipping(@NotNull final Long productId, @NotNull final ProductShippingData data) {
		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toProduct(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

	/* ################ Opérations de Opérations de création ################ */

	/**
	 * Opération de création d'un nouveau produit dans une langue
	 * 
	 * @param languageCode
	 * @param data
	 * @return
	 */
	@Transactional
	public final Long createProductLang(@NotNull final LanguageCode languageCode, @Valid final ProductData data) {
		final ProductLang productLang = mapper.toProductLang(data);
		productLang.setLanguage(languageCode);
		productLang.persistAndFlush();

		return productLang.getProduct().getId();
	}

	/* ################ Opérations de suppression ################ */

	/**
	 * Opération de suppresion d'un produit existant, traduit dans une langue
	 * 
	 * @param languageCode
	 * @param productId
	 */
	@Transactional
	public void deleteProductLang(@NotNull final LanguageCode languageCode, @NotNull final Long productId) {
		boolean exists = ProductLang.deleteByProductLangPK(productId, languageCode);
		if (!exists) {
			throw new NotFoundException(
					String.format("Suppression impossible, produit introuvable avec productId=%s, languageCode=%s",
							String.valueOf(productId), languageCode.toString()));
		}
	}

}