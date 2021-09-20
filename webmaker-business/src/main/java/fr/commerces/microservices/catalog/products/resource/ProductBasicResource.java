package fr.commerces.microservices.catalog.products.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.products.manager.ProductBasicManager;
import fr.commerces.microservices.catalog.products.openapi.ProductBasicResourceApi;
import fr.webmaker.microservices.catalog.products.data.ProductBasicData;


@RequestScoped
public class ProductBasicResource extends GenericResource implements ProductBasicResourceApi {

	@Inject
	ProductBasicManager manager;

	@Override
	public Response getProductBasicById(String languageCode, Long idProduct) {
		final ProductBasicData data = manager.findProductBasicByProductAndLang(idProduct,
				LanguageCode.getByCode(languageCode));

		return buildResponse(data, idProduct);
	}

	@Override
	public Response patchProductBasic(final String languageCode, final Long productId, final ProductBasicData data) {
		manager.updateProductBasic(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}