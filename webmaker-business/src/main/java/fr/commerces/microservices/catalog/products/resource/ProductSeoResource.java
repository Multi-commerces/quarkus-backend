package fr.commerces.microservices.catalog.products.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.products.manager.ProductSeoManager;
import fr.commerces.microservices.catalog.products.openapi.ProductSeoApi;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.data.ProductSeoData;

@RequestScoped
public class ProductSeoResource extends GenericResource implements ProductSeoApi {

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
		final List<SingleCompositeData<ProductSeoData, Identifier<Long>>> collection = new ArrayList<>();
		items.entrySet().stream().forEach(entry -> {
			final SingleCompositeData<ProductSeoData, Identifier<Long>> singleResponse = new SingleCompositeData<ProductSeoData, Identifier<Long>>();
			singleResponse.setIdentifier(new Identifier<Long>(productId));
			singleResponse.setData(entry.getValue());

			collection.add(singleResponse);
		});
		//reponse.setCollection(collection);

		return reponse;
	}

	@Override
	public SingleCompositeData<ProductSeoData, Identifier<Long>> getProductSeo(final String lang, final Long productId) {
		final LanguageCode languageCode = LanguageCode.getByCode(lang);

		// CALL BUSINESS
		final ProductSeoData data = manager.findSeoByProductLangPK(productId, languageCode);

		// RESPONSE
		final SingleCompositeData<ProductSeoData, Identifier<Long>> response = new SingleCompositeData<ProductSeoData, Identifier<Long>>();
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