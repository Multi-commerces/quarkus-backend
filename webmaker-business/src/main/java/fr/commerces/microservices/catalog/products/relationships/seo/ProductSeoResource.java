package fr.commerces.microservices.catalog.products.relationships.seo;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.product.ProductSeoData;

@RequestScoped
public class ProductSeoResource extends JsonApiResource implements ProductSeoApi {

	@Inject
	ProductSeoManager manager;

	@Override
	public Map<LanguageCode, ProductSeoData> getProductSeos(final Long productId) {

	
		return manager.findSeoByProduct(productId);
	}

	@Override
	public ProductSeoData getProductSeo(final String lang, final Long productId) {
		final LanguageCode languageCode = LanguageCode.getByCode(lang);

		// CALL BUSINESS
		return manager.findSeoByProductLangPK(productId, languageCode);
	}

	@Override
	public Response updateProductSeo(final String languageCode, final Long productId, final ProductSeoData data) {
		manager.updateSEO(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}