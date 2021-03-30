package fr.commerces.services.products.manager;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.logged.Logged;
import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductSeoData;
import fr.commerces.services.products.entity.Product;
import fr.commerces.services.products.entity.ProductLang;
import fr.commerces.services.products.mapper.ProductMapper;

/**
 * Service manager des produits SEO
 * 
 * @author Julien ILARI
 *
 */
@Logged
@ApplicationScoped
public class ProductSeoManager {

	/**
	 * Mapper produit
	 */
	@Inject
	ProductMapper mapper;

	/**
	 * Fonction Mapper POJO > GenericResponse
	 */
	Function<ProductLang, GenericResponse<ProductSeoData, Long>> bindSeo = pojo -> {
		GenericResponse<ProductSeoData, Long> response = mapper.toProductSeoResponse(pojo);
		return response;
	};

	/**
	 * Liste SEO du produit (seo multi-langues)
	 * 
	 * @param productId identifiant du produit
	 * @param language  langue du produit
	 * @return
	 */
	public final Collection<GenericResponse<ProductSeoData, Long>> findProductSeoByProduct(final Long productId) {
		try (Stream<ProductLang> streamEntity = Product.<Product>findByIdOptional(productId)
				.orElseThrow(NotFoundException::new).getProductLang().stream()) {
			return streamEntity.map(bindSeo).collect(Collectors.toList());
		}
	}

	/**
	 * Produit SEO
	 * 
	 * @param productId identifiant du produit
	 * @param language  langue du produit
	 * @return
	 */
	public final GenericResponse<ProductSeoData, Long> findProductSeoByProductLang(final Long productId,
			final LanguageCode language) {
		ProductLang pojo = ProductLang.findByIdProductAndLanguageCode(productId, language)
				.orElseThrow(NotFoundException::new);
		return mapper.toProductSeoResponse(pojo);
	}

	/**
	 * Opération de mise à jour Produit SEO
	 * 
	 * @param languageCode langue du produit
	 * @param productId    identifiant du produit
	 * @param data
	 */
	@Transactional
	public final void update(final LanguageCode languageCode, final Long productId, final ProductSeoData data) {
		ProductLang.findByIdProductAndLanguageCode(productId, languageCode)
				.map(pojo -> mapper.dataIntoEntity(data, pojo)).orElseThrow(NotFoundException::new);
	}

}