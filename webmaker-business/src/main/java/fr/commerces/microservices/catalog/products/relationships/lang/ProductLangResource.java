package fr.commerces.microservices.catalog.products.relationships.lang;

import java.net.URI;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.basic.CategoryManager;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductLangData;

public class ProductLangResource extends JsonApiResource<ProductLangData> implements ProductLangResourceApi {

	@Inject
	ProductLangManager manager;

	@Inject
	CategoryManager categoryManager;

	@PostConstruct
	public void ProductLangResourcePostConstruct() {
		converter = new ResourceConverter(objectMapper, ProductLangCompositeData.class, ProductCompositeData.class,
				ProductLangData.class, ProductData.class);

		converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
	}

	/**
	 * GET Product-LANG ALL
	 */
	@Override
	public byte[] getProductLangs(Long productId) {
		var data = manager.findAll(productId);

		return writeDocument(data);
	}

	/**
	 * GET Product-LANG BY ID&LANG
	 */
	@Override
	public byte[] getProductLangById(final LanguageCode languageCode, final Long idProduct) {
		return writeDocument(manager.findByPK(idProduct, languageCode));
	}

	/**
	 * CREATE Product-LANG
	 */
	@Override
	public Response createProductLang(final LanguageCode languageCode, final Long productId, final  byte[] flux) {
		var newData = manager.createProductLang(productId, languageCode, readData(flux));

		final URI uri = uriInfo.getAbsolutePathBuilder().build();
		return Response.created(uri).language(Locale.FRENCH).entity(newData).build();
	}

	/**
	 * DELETE Product-LANG BY ID&LANG
	 */
	@Override
	public Response deleteProductLang(final LanguageCode languageCode, final Long productId) {
		manager.deleteProductLang(languageCode, productId);
		return Response.noContent().build();
	}

	/**
	 * PATCH Product-LANG BY ID&LANG
	 */
	@Override
	public Response putProductLang(final LanguageCode languageCode, final Long productId, final ProductLangData data) {
		manager.updateProductLang(languageCode, productId, data);
		return Response.noContent().build();
	}

}