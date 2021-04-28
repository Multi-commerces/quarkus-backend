package fr.commerces.services.products.ressources.seo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services._transverse.response.SingleResponse;
import fr.commerces.services.products.data.ProductSeoData;
import fr.commerces.services.products.manager.ProductSeoManager;

@RequestScoped
public class ProductSeoResource extends GenericResource<CollectionResponse<ProductSeoData, Long>>
		implements ProductSeoApi {

	@Inject
	ProductSeoManager manager;

	@Override
	public CollectionResponse<ProductSeoData, Long> getProductSeos(final Long productId) {
		final Map<LanguageCode, ProductSeoData> items = manager.findProductSeoByProduct(productId);

		/*
		 * Réponse API
		 */
		final CollectionResponse<ProductSeoData, Long> reponse = new CollectionResponse<ProductSeoData, Long>();

		// embedded
		final List<SingleResponse<ProductSeoData, Long>> embedded = new ArrayList<>();
		items.entrySet().stream().forEach(entry -> {
			SingleResponse<ProductSeoData, Long> singleResponse = new SingleResponse<ProductSeoData, Long>();
			singleResponse.setId(productId);
			singleResponse.setLanguageCode(entry.getKey());
			singleResponse.setData(entry.getValue());

			embedded.add(singleResponse);
		});
		reponse.setEmbedded(embedded);

		return null;
	}

	@Override
	public SingleResponse<ProductSeoData, Long> getProductSeo(final String lang, final Long productId) {
		final LanguageCode languageCode = LanguageCode.getByCode(lang);

		// CALL BUSINESS
		final ProductSeoData data = manager.findProductSeoByProductLang(productId, languageCode);

		// RESPONSE
		final SingleResponse<ProductSeoData, Long> response = new SingleResponse<ProductSeoData, Long>();
		response.setData(data);
		response.setId(productId);
		response.setLanguageCode(languageCode);

		return response;
	}

	@Override
	public Response updateProductSeo(final String languageCode, final Long productId, final ProductSeoData data) {
		manager.update(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}