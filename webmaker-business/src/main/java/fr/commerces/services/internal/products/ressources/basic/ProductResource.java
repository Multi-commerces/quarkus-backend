package fr.commerces.services.internal.products.ressources.basic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services._transverse.GenericResource;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductBasicData;
import fr.webmaker.product.data.ProductData;


@RequestScoped
public class ProductResource extends GenericResource<CollectionResponse<ProductData, ProductID>>
		implements ProductBasicResourceApi {

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