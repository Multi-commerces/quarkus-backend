package fr.commerces.microservices.catalog.products.relations.seo;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.self.ProductManager;
import fr.commerces.microservices.catalog.products.self.model.ProductInclude;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductSeoData;

@RequestScoped
public class ProductSeoResource extends JsonApiResource<ProductSeoData> implements ProductSeoApi {

//	@Inject
//	ProductManager productManager;

	@Inject
	ProductSeoManager manager;

	@Inject
	ProductManager managerProduct;

	public ProductSeoResource() {
		super(ProductSeoData.class);
	}

	@Override
	public Response getRelationShipsProductSeo(final long productId, final LanguageCode lang) {
		return writeRelationships(
				managerProduct.findById(productId, LanguageCode.undefined, List.of(ProductInclude.LANGS)).getLanguages()
						.stream().map(ProductLangCompositeData::getSeo).collect(Collectors.toList()));
	}

	@Override
	public Response getProductSeos(final long productId, final LanguageCode languageCode) {
		var data = managerProduct.findById(productId, languageCode, List.of(ProductInclude.LANGS)).getLanguages()
				.stream().map(ProductLangCompositeData::getSeo).collect(Collectors.toList());

		return writeResponse(data);
	}

	@Override
	public Response getProductSeo(final long productId, final LanguageCode languageCode) {
		
		// CALL BUSINESS
		return writeResponse(manager.findSeoByProductLangPK(productId, languageCode));
	}

	@Override
	public Response patchProductSeo(final long productId, final LanguageCode languageCode, final byte[] data) {
		manager.updateSEO(languageCode, productId, readData(data));
		return Response.noContent().build();
	}

}