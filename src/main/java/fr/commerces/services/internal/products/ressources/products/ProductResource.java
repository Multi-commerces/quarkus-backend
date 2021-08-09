package fr.commerces.services.internal.products.ressources.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.hypermedia.Hypermedia;
import fr.commerces.hypermedia.HypermediaApi;
import fr.commerces.hypermedia.HypermediaLink;
import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.data.PagingData;
import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services._transverse.response.SingleResponse;
import fr.commerces.services.internal.products.data.ProductData;
import fr.commerces.services.internal.products.manager.ProductManager;
import fr.commerces.services.internal.products.ressources.deliveries.ProductShippingApi;
import fr.commerces.services.internal.products.ressources.pricing.ProductPricingResource;
import fr.commerces.services.internal.products.ressources.seo.ProductSeoApi;
import fr.commerces.services.internal.products.ressources.stock.ProductStockResource;
import fr.commerces.services.internal.products.ressources.variations.ProductVariationApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@HypermediaApi(
	resource = ProductResourceApi.class, title = "Informations basiques du produit", 
	links = {
		@HypermediaLink(resource = ProductSeoApi.class, methode = "getProductSeo", rel = "seo"),
		@HypermediaLink(resource = ProductStockResource.class, rel = "stock"),
		@HypermediaLink(resource = ProductPricingResource.class, rel = "price"),
		@HypermediaLink(resource = ProductShippingApi.class, methode = "getProductShipping", rel = "shipping"),
		@HypermediaLink(resource = ProductVariationApi.class, rel = "variation") 
	})
@RequestScoped
public class ProductResource extends GenericResource<CollectionResponse<ProductData, Long>>
		implements ProductResourceApi {

	@Inject
	ProductManager manager;

	@Override
	public Response getProductById(final String languageCode, final Long idProduct) {
		// Data API
		final ProductData data = manager.findByProductLangPK(idProduct, LanguageCode.getByCode(languageCode));

		// Réponse API
		final SingleResponse<ProductData, Long> singleResponse = new SingleResponse<ProductData, Long>();
		singleResponse.setId(idProduct);
		singleResponse.setData(data);

		if(log.isDebugEnabled())
		{
			log.debug(singleResponse.toString());
		}
		return Response.ok(singleResponse).build();
	}

	@Hypermedia
	@Override
	public CollectionResponse<ProductData, Long> getProducts(final String languageCode, final Integer page,
			final Integer size) {	
		/*
		 * Data API
		 */
		final Map<Long, ProductData> items = manager.findAllByLanguageCode(LanguageCode.getByCode(languageCode),
				Optional.ofNullable(page), Optional.ofNullable(size));

		/*
		 *  Réponse API
		 */
		final CollectionResponse<ProductData, Long> reponse = new CollectionResponse<ProductData, Long>();
		
		// embedded
		final List<SingleResponse<ProductData, Long>> embedded = new ArrayList<>();
		items.entrySet().stream().forEach(entry -> {
			SingleResponse<ProductData, Long> singleResponse = new SingleResponse<ProductData, Long>();
			singleResponse.setId(entry.getKey());
			singleResponse.setData(entry.getValue());

			embedded.add(singleResponse);
		});
		reponse.setEmbedded(embedded);
		
		// Pagination
		final PagingData pagingData = manager.getPagingProductLang(LanguageCode.getByCode(languageCode), Optional.ofNullable(size));
		reponse.setPaging(pagingData);

		return reponse;
	}

	@Override
	public Response createProduct(final String languageCode, final ProductData data) {
		Long newId = manager.createProductLang(LanguageCode.getByCode(languageCode), data);
		return buildResponse.apply(newId);
	}

	@Override
	public Response updateProduct(final String languageCode, final Long productId, final ProductData data) {
		manager.updateProductLang(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

	@Override
	public Response deleteProductLang(final String languageCode, final Long productId) {
		manager.deleteProductLang(LanguageCode.getByCode(languageCode), productId);
		return Response.noContent().build();
	}

}