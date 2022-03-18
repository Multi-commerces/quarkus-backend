package fr.commerces.microservices.catalog.products.relationships.lang;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductLangData;

public class ProductLangResource extends JsonApiResource<ProductLangData> implements ProductLangResourceApi {

	@Inject
	ProductLangManager manager;

	public ProductLangResource() {
		super(ProductLangData.class, ProductLangCompositeData.class);
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
	public Response patchProductLang(final LanguageCode languageCode, final Long productId, final byte[] flux) {
		try {
			objectMapper.readTree(flux).toPrettyString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		manager.updateProductLang(languageCode, productId, readData(flux));
		return Response.noContent().build();
	}

}