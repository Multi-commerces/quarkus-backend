package fr.commerces.microservices.catalog.products;

import java.util.List;
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
import fr.commerces.microservices.catalog.products.lang.ProductLangData;
import fr.webmaker.commons.PagingData;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@ManagerInterceptor
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
	public final List<ProductLangData> findAllByLanguageCode(@NotNull final LanguageCode language,
			@NotNull final Optional<@Min(1) Integer> page, 
			@NotNull final Optional<@Positive @Min(1) Integer> size
			) {
		
		return ProductLang.findByLanguageCode(language)
				.page(Page.of(page.orElse(1) - 1, size.orElse(SIZE)))
				.stream()
				.map(mapper::toProductData)
				.collect(Collectors.toList());
	}
	
	public final List<ProductDepth1Data> findAll() {
		final List<Product> values = Product.<Product>findAll().list();
		return values.stream().map(mapper::toDepth1).collect(Collectors.toList());
	}

	/**
	 * <h1>GET BY productId</h1> 
	 * 
	 * <p>Opération de rcherche du produit par identifiant</p>
	 * 
	 * @param productId    identifiant
	 * @param languageCode code langue
	 * @return
	 * @throws NotFoundException Produit non trouvé
	 */
	public final ProductDepth1Data findById(@NotNull final Long productId) throws NotFoundException {
		return Product.<Product>findByIdOptional(productId)
				.map(mapper::toDepth1Data)
				.orElseThrow(() -> new NotFoundException(productId));
	}

	/* ################ Opérations de mise à jour ################ */

	/**
	 * <h1>UPDATE BY productId AND languageCode</h1>
	 * <p>
	 * Opération de mise à jour d'un produit
	 * </p>
	 * 
	 * @param productId identifiant du produit
	 * @param data      les données du produit
	 * @throws NotFoundException produit introuvable
	 */
	@Transactional
	public final void updateProduct(@NotNull final Long productId,
			@NotNull @Valid final ProductData data) throws NotFoundException {

		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toProduct(data, pojo))
				.orElseThrow(() -> new NotFoundUpdateException(productId));
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
	public void deleteProduct(@NotNull final Long productId) throws NotFoundDeleteException {
		if (!Product.deleteById(productId)) {
			throw new NotFoundDeleteException(productId);
		}
	}

}