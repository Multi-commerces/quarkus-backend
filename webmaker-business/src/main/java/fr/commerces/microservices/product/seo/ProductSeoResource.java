package fr.commerces.microservices.product.seo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.GenericResource;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.data.ProductSeoData;

@RequestScoped
public class ProductSeoResource extends GenericResource<CollectionResponse<ProductSeoData, Identifier<Long>>>
		implements ProductSeoApi {

	@Inject
	ProductSeoManager manager;

	@Override
	public CollectionResponse<ProductSeoData, Identifier<Long>> getProductSeos(final Long productId) {
		final Map<LanguageCode, ProductSeoData> items = manager.findSeoByProduct(productId);

		/*
		 * Réponse API
		 */
		final CollectionResponse<ProductSeoData, Identifier<Long>> reponse = new CollectionResponse<ProductSeoData, Identifier<Long>>();

		// embedded
		final List<SingleResponse<ProductSeoData, Identifier<Long>>> embedded = new ArrayList<>();
		items.entrySet().stream().forEach(entry -> {
			final SingleResponse<ProductSeoData, Identifier<Long>> singleResponse = new SingleResponse<ProductSeoData, Identifier<Long>>();
			singleResponse.setIdentifier(new Identifier<Long>(productId));
			singleResponse.setData(entry.getValue());

			embedded.add(singleResponse);
		});
		reponse.set_embedded(embedded);

		return null;
	}

	@Override
	public SingleResponse<ProductSeoData, Identifier<Long>> getProductSeo(final String lang, final Long productId) {
		final LanguageCode languageCode = LanguageCode.getByCode(lang);

		// CALL BUSINESS
		final ProductSeoData data = manager.findSeoByProductLangPK(productId, languageCode);

		// RESPONSE
		final SingleResponse<ProductSeoData, Identifier<Long>> response = new SingleResponse<ProductSeoData, Identifier<Long>>();
		response.setData(data);
		response.setIdentifier(new Identifier<Long>(productId));

		return response;
	}

	@Override
	public Response updateProductSeo(final String languageCode, final Long productId, final ProductSeoData data) {
		manager.updateSEO(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}