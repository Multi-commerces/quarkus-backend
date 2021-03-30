package fr.commerces.services.products.ressources.seo;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductSeoData;
import fr.commerces.services.products.manager.ProductSeoManager;

@RequestScoped
public class ProductSeoResource implements ProductSeoApi {

	@Inject
	ProductSeoManager manager;

	@Override
	public Collection<GenericResponse<ProductSeoData, Long>> getProductSeos(final Long productId) {
		return manager.findProductSeoByProduct(productId);
	}

	@Override
	public GenericResponse<ProductSeoData, Long> getProductSeo(final String languageCode, final Long productId) {
		return manager.findProductSeoByProductLang(productId, LanguageCode.getByCode(languageCode));
	}

	@Override
	public Response updateProductSeo(final String languageCode, final Long productId, final ProductSeoData data) {
		manager.update(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}