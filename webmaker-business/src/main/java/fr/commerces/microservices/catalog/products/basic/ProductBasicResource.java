package fr.commerces.microservices.catalog.products.basic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.GenericResource;

@RequestScoped
public class ProductBasicResource extends GenericResource implements ProductBasicResourceApi {

	@Inject
	ProductBasicManager manager;

	public ProductBasicResource() {
		converter = new ResourceConverter(objectMapper, ProductBasicData.class);
	}

	@Override
	public byte[] getProductBasicById(Long idProduct) throws DocumentSerializationException {
		final ProductBasicData data = manager.findProductBasicByProductAndLang(idProduct);

		return converter.writeDocument(new JSONAPIDocument<ProductBasicData>(data));
	}

	@Override
	public Response patchProductBasic(final String languageCode, final Long productId, final ProductBasicData data) {
		manager.updateProductBasic(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}