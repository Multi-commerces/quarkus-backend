package fr.commerces.services.products.manager;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.logged.Logged;
import fr.commerces.services._transverse.data.PagingData;
import fr.commerces.services.products.data.ProductData;
import fr.commerces.services.products.data.ProductShippingData;
import fr.commerces.services.products.entity.Product;
import fr.commerces.services.products.entity.ProductLang;
import fr.commerces.services.products.mapper.ProductMapper;
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

	/**
	 * Obtenir la liste des produits avec prise en compte des critères de recherche
	 * 
	 * @param language 
	 * @param page (optionnel)
	 * @param size (optionnel)
	 * @return liste des produits (key : identifiant du produit, value : le produit)
	 */
	public final Map<Long, ProductData> list(final LanguageCode language, final Optional<Integer> page,
			final Optional<Integer> size) {
		try (final Stream<ProductLang> streamEntity = ProductLang.findByLanguageCode(language)
				.page(Page.of(page.orElse(1) - 1, size.orElse(SIZE))).stream()) {
			return mapper
					.toProductDataById(streamEntity.collect(Collectors.toMap(ProductLang::getId, Function.identity())));
		}
	}

	/**
	 * Obtenir la pagination
	 * @param language
	 * @param optSize
	 * @return
	 */
	public final PagingData getPagingData(final LanguageCode language, final Optional<Integer> optSize) {
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
	 * Obtenir les informations d'expédition du produit
	 * 
	 * @param productId
	 * @return
	 */
	public final ProductShippingData findProductShippingById(final Long productId) {
		final Product product = Product.<Product>findByIdOptional(productId).orElseThrow(NotFoundException::new);
		return mapper.toProductShippingResponse(product);
	}

	/**
	 * Mise à jour des informations d'expédition du produit
	 * 
	 * @param language
	 * @param id
	 * @param data
	 */
	@Transactional
	public final void updateProductShipping(final Long productId, final ProductShippingData data) {
		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toProduct(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

	public final ProductData findByIdProductAndLanguageCode(final Long productId, final LanguageCode language) {
		ProductLang entity = ProductLang.findByIdProductAndLanguageCode(productId, language)
				.orElseThrow(NotFoundException::new);

		return mapper.toProductData(entity);
	}

	@Transactional
	public final void update(final LanguageCode language, final Long id, final ProductData data) {
		ProductLang.findByIdProductAndLanguageCode(id, language).map(pojo -> mapper.toProductLang(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

	@Transactional
	public final Long create(final LanguageCode languageCode, final ProductData data) {
		final ProductLang productLang = mapper.toProductLang(data);
		productLang.setLanguage(languageCode);
		productLang.persistAndFlush();

		return productLang.getProduct().getId();
	}

	@Transactional
	public void delete(final LanguageCode languageCode, final Long productId) {
		boolean isOK = ProductLang.deleteByIdProductAndLanguageCode(productId, languageCode);
		if (!isOK) {
			throw new NotFoundException(
					String.format("Suppression impossible, produit introuvable avec productId=%s, languageCode=%s",
							String.valueOf(productId), languageCode.toString()));
		}
	}

}