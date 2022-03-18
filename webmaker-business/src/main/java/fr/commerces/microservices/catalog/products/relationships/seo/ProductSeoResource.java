package fr.commerces.microservices.catalog.products.relationships.seo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.product.ProductSeoData;

@RequestScoped
public class ProductSeoResource extends JsonApiResource<ProductSeoData> implements ProductSeoApi {

//	@Inject
//	ProductManager productManager;
	
	@Inject
	ProductSeoManager manager;
	
	public ProductSeoResource() {
		super(ProductSeoData.class);
	}

	@Override
	public Response getProductSeos(final Long productId) {
		
//		return writeJsonApiResponse(
//				productManager.findById(productId, List.of(ProductRelation.SEO)).getSeo()
//				);
		
		return writeJsonApiResponse(manager.findSeoByProduct(productId).get(LanguageCode.fr));
	}

	@Override
	public Response getProductSeo(final String lang, final Long productId) {
		final LanguageCode languageCode = LanguageCode.getByCode(lang);

		// CALL BUSINESS
		return writeJsonApiResponse(manager.findSeoByProductLangPK(productId, languageCode));
	}

	@Override
	public Response patchProductSeo(final String languageCode, final Long productId, final byte[] data) {
		manager.updateSEO(LanguageCode.getByCode(languageCode), productId, readData(data));
		return Response.noContent().build();
	}

}