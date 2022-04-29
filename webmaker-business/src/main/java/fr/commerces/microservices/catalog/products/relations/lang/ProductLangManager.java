package fr.commerces.microservices.catalog.products.relations.lang;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.BadRequestException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.self.ProductMapper;
import fr.webmaker.annotation.ManagerInterceptor;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductLangData;
import fr.webmaker.exception.crud.NotFoundCreateException;
import fr.webmaker.exception.crud.NotFoundDeleteException;
import fr.webmaker.exception.crud.NotFoundException;
import fr.webmaker.exception.crud.NotFoundUpdateException;
import fr.webmaker.restfull.hateos.PagingData;
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
	
	@Inject
	ProductMapper productMapper;

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
	 * Obtenir la liste des produits dans une langue
	 * 
	 * @param language
	 * @param page
	 * @param size
	 * @return
	 */
	@Transactional
	public final List<ProductLangCompositeData> findAllByLanguageCode(@NotNull final LanguageCode language,
			final Optional<@Min(1) Integer> page, final Optional<@Positive @Min(1) Integer> size) {
		
		return ProductLang.findByLanguageCode(language)
				.page(Page.of(page.orElse(1) - 1, size.orElse(SIZE)))
				.stream()
				.map(mapper::toCompositeData)
				.collect(Collectors.toList());
	}
	
	/**
	 * Obtenir les produits traduit dans toutes les langues 
	 * @param productId
	 * @return
	 */
	public final List<ProductLangCompositeData> findAll(@NotNull final Long productId) {
		return ProductLang.findByProductId(productId, ProductLangInclusion.PRODUCT)
				.stream()
				.map(mapper::toCompositeData)
				.collect(Collectors.toList());
	}
	
	/**
	 * Obtenir les produits avec les traductions
	 * @param productIds
	 * @return
	 */
	public final Map<Long, List<ProductLangCompositeData>> findByProductIds(@NotNull final Collection<Long> productIds
			,final LanguageCode languageCode) {
		try (final Stream<ProductLang> streamEntity = ProductLang.findByProductIdsAndLanguageCode(productIds, languageCode)
				.stream()) {
			return streamEntity
					.collect(Collectors.groupingBy(o -> o.getProduct().getId(),
							Collectors.mapping(mapper::toCompositeData, Collectors.toList())));
		}
	}
	

	/**
	 * <h1>GET BY productId AND languageCode</h1> 
	 * <p>Otenir un produit dans une langue</p>
	 * 
	 * @param productId    identifiant
	 * @param languageCode code langue
	 * @return
	 * @throws NotFoundException Produit non trouvé
	 */
	public final ProductLangCompositeData findByPK(@NotNull final Long productId,
			@NotNull final LanguageCode languageCode) throws NotFoundException {
		return mapper.toCompositeData(ProductLang.findByProductIdAndLanguageCode(productId, languageCode)
				.orElseThrow(() -> new NotFoundException(productId)));
	}

	public final List<ProductLangCompositeData> findByProductIdsAndLanguageCode(@NotNull final Collection<Long> productIds,
			@NotNull final LanguageCode languageCode) throws NotFoundException {
		return mapper.toCollectionCompositeData(ProductLang.findByProductIdsAndLanguageCode(productIds, languageCode));
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
		ProductLang.findByProductIdAndLanguageCode(productId, language).map(pojo -> mapper.toExistingEntity(data, pojo))
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
	public final ProductLangCompositeData createProductLang(@NotNull final Long productId, @NotNull final LanguageCode languageCode, @Valid final ProductLangData data) {
		var product = Product.<Product>findByIdOptional(productId)
				.orElseThrow(() -> new NotFoundCreateException(productId));

		var productLang = mapper.toEntity(data);
		productLang.setProductLangPK(productId, languageCode);
		productLang.setProduct(product);
		
		if (product.getProductLangs().stream().map(ProductLang::getLang)
				.collect(Collectors.toList()).contains(languageCode)) {
			throw new BadRequestException("Lang-Produit déjà existante pour la paire : (" + productId.toString() + ","
					+ languageCode.getName() + ")");
		}

		productLang.persist();

		return mapper.toCompositeData(productLang);
	}

	/* ################ Opérations de suppression ################ */

	/**
	 * Opération de suppresion d'un produit (traduit dans une langue) existant
	 * 
	 * @param languageCode code langue du produit
	 * @param productId    identifiant du produit
	 */
	@Transactional
	public final void deleteProductLang(@NotNull final LanguageCode languageCode, @NotNull final Long productId) {
		if (!ProductLang.deleteByProductLangPK(productId, languageCode)) {
			throw new NotFoundDeleteException(
					String.format("productId=%s, languageCode=%s", String.valueOf(productId), languageCode.toString()));
		}
	}

}