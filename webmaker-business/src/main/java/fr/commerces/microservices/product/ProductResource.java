package fr.commerces.microservices.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.hypermedia.Hypermedia;
import fr.commerces.commons.hypermedia.HypermediaApi;
import fr.commerces.commons.hypermedia.HypermediaLink;
import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.product.basic.ProductBasicResourceApi;
import fr.commerces.microservices.product.deliveries.ProductShippingApi;
import fr.commerces.microservices.product.pricing.ProductPricingApi;
import fr.commerces.microservices.product.seo.ProductSeoApi;
import fr.commerces.microservices.product.stocks.ProductStockApi;
import fr.commerces.microservices.product.variations.ProductVariationApi;
import fr.webmaker.common.PagingData;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductData;
import fr.webmaker.product.response.ProductDataResponse;

@HypermediaApi(resource = ProductBasicResourceApi.class, title = "Informations basiques du produit", links = {
		@HypermediaLink(resource = ProductSeoApi.class, methode = "getProductSeo", rel = "seo"),
		@HypermediaLink(resource = ProductStockApi.class, methode = "getProductStock", rel = "stock"),
		@HypermediaLink(resource = ProductPricingApi.class, methode = "getProductShipping", rel = "price"),
		@HypermediaLink(resource = ProductShippingApi.class, methode = "getProductShipping", rel = "shipping"),
		@HypermediaLink(resource = ProductVariationApi.class, methode = "getVariations", rel = "variations") })
@RequestScoped
public class ProductResource extends GenericResource<CollectionResponse<ProductData, ProductID>>
		implements ProductResourceApi {

	@Inject
	ProductManager manager;

	/**
	 * GET BY ID
	 */
	@Override
	public Response getProductById(final String languageCode, final Long idProduct, Boolean includeBasic) {
		final ProductData data = manager.findByProductLangPK(idProduct, LanguageCode.getByCode(languageCode));
		
		return buildResponse(data, new ProductID(idProduct, languageCode));
	}

	/**
	 * GET ALL
	 */
	@Hypermedia
	@Override
	public CollectionResponse<ProductData, ProductID> getProducts(final String languageCode, final Integer page,
			final Integer size) {

		final LanguageCode langCode = LanguageCode.getByCode(languageCode);

		/*
		 * Data API
		 */
		final Map<Long, ProductData> items = manager.findAllByLanguageCode(langCode, Optional.ofNullable(page),
				Optional.ofNullable(size));

		/*
		 * Réponse API
		 */
		final CollectionResponse<ProductData, ProductID> reponse = new CollectionResponse<ProductData, ProductID>();

		// Réponse API - embedded
		final List<SingleResponse<ProductData, ProductID>> embedded = new ArrayList<>();
		items.entrySet().stream().forEach(entry -> {
			embedded.add(new ProductDataResponse(entry.getKey(), languageCode, entry.getValue()));
		});
		reponse.set_embedded(embedded);

		// Réponse API - Pagination
		final PagingData pagingData = manager.getPagingProductLang(LanguageCode.getByCode(languageCode), Optional.ofNullable(size));
		reponse.set_paging(pagingData);

		return reponse;
	}

	/**
	 * CREATE BY LANG
	 */
	@Override
	public Response createProductLang(final String languageCode, final ProductData data) {
		Long newId = manager.createProductLang(LanguageCode.getByCode(languageCode), data);
		return buildResponse.apply(newId);
	}

	/**
	 * DELETE BY ID & LANG
	 */
	@Override
	public Response deleteProductLang(final String languageCode, final Long productId) {
		manager.deleteProductLang(LanguageCode.getByCode(languageCode), productId);
		return Response.ok().build();
	}

	/**
	 * PATCH BY ID & LANG
	 */
	@Override
	public Response patchProductLang(final String languageCode, final Long productId, final ProductData data) {
		manager.updateProductLang(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}

}