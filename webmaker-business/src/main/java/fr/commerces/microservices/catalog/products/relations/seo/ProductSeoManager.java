package fr.commerces.microservices.catalog.products.relations.seo;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.webmaker.annotation.ManagerInterceptor;
import fr.webmaker.data.product.ProductSeoData;

@ManagerInterceptor
@ApplicationScoped
public class ProductSeoManager {

	@Inject
	ProductSeoMapper mapper;

	/**
	 * Otenir les informations SEO du produit (toutes les langues)
	 * 
	 * @param productId identifiant du produit
	 * @param language  langue du produit
	 * @return
	 */
	public final Map<LanguageCode, ProductSeoData> findSeoByProduct(@NotNull final Long productId) {
		try (Stream<ProductLang> streamEntity = Product.<Product>findByIdOptional(productId)
				.orElseThrow(NotFoundException::new).getProductLangs().stream()) {
			return mapper.toMap(
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
		return ProductLang.findByProductIdAndLanguageCode(productId, languageCode).map(mapper::toData)
				.orElseThrow(NotFoundException::new);
	}

	/**
	 * Opération de mise à jour Produit SEO
	 * 
	 * @param languageCode langue
	 * @param productId    identifiant
	 * @param data         infos sur le produit
	 * @throws NotFoundException produit introuvale
	 */
	@Transactional
	public final void updateSEO(@NotNull final LanguageCode languageCode, @NotNull final Long productId,
			@Valid final ProductSeoData data) throws NotFoundException {
		ProductLang.findByProductIdAndLanguageCode(productId, languageCode)
				.map(pojo -> mapper.toEntity(data, pojo)).orElseThrow(NotFoundException::new);
	}

}