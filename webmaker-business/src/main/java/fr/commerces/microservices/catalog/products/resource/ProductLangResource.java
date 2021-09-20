package fr.commerces.microservices.catalog.products.resource;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.hypermedia.Hypermedia;
import fr.commerces.commons.hypermedia.HypermediaApi;
import fr.commerces.commons.hypermedia.HypermediaLink;
import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.commerces.microservices.catalog.products.manager.ProductLangManager;
import fr.commerces.microservices.catalog.products.openapi.ProductBasicResourceApi;
import fr.commerces.microservices.catalog.products.openapi.ProductLangResourceApi;
import fr.commerces.microservices.catalog.products.openapi.ProductPricingApi;
import fr.commerces.microservices.catalog.products.openapi.ProductResourceApi;
import fr.commerces.microservices.catalog.products.openapi.ProductSeoApi;
import fr.commerces.microservices.catalog.products.openapi.ProductShippingApi;
import fr.commerces.microservices.catalog.products.openapi.ProductStockApi;
import fr.commerces.microservices.catalog.products.openapi.ProductVariationApi;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.data.ProductLangData;
import fr.webmaker.microservices.catalog.products.id.ProductLangID;
import fr.webmaker.microservices.catalog.products.response.ProductLangResponse;
import lombok.Getter;

@HypermediaApi(resource = ProductBasicResourceApi.class, 
	title = "Informations sur les produits", 
	links = {
		@HypermediaLink(resource = ProductSeoApi.class, methode = "getProductSeo", rel = "seo"),
		@HypermediaLink(resource = ProductStockApi.class, methode = "getProductStock", rel = "stock"),
		@HypermediaLink(resource = ProductPricingApi.class, methode = "getProductShipping", rel = "price"),
		@HypermediaLink(resource = ProductShippingApi.class, methode = "getProductShipping", rel = "shipping"),
		@HypermediaLink(resource = ProductVariationApi.class, methode = "getVariations", rel = "variations"),
		@HypermediaLink(resource = ProductResourceApi.class, methode = "getCategories", rel = "categories") 
	})
@RequestScoped
public class ProductLangResource extends GenericResource implements ProductLangResourceApi {

	@Inject
	ProductLangManager manager;
	
	@Inject
	CategoryManager categoryManager;
	
	@Context @Getter
	UriInfo uriInfo;

	/**
	 * GET ALL
	 */
	@Hypermedia
	@Override
	public CollectionResponse<ProductLangData, ProductLangID> getProducts(Long productId) {
		// Recherche des produits
		Map<LanguageCode, ProductLangData> productByProductId = manager.findAll(productId);
		
		/*
		 * Collection
		 */
		final List<SingleCompositeData<ProductLangData, ProductLangID>> collection = productByProductId.entrySet().stream()
				// map (encapsulation dans un ProductResponse)
				.map(item ->  {
					ProductLangResponse resp = new ProductLangResponse(productId, item.getKey().getName(), item.getValue());	
					
					return resp;
				})
				// collect
				.collect(Collectors.toList());

		/*
		 * CollectionResponse
		 */
		return CollectionResponse.<ProductLangData, ProductLangID>builder(uriInfo)
			// collection
			.collection(collection)
			.build();
	}
	
	/**
	 * GET BY ID
	 */
	@Override
	public Response getProductById(final String languageCode, final Long idProduct, Boolean includeBasic) {
		final ProductLangData data = manager.findByProductLangPK(idProduct, LanguageCode.getByCode(languageCode));
	uriInfo.getPath();
		 Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                 .rel("self").build();
		 
		return Response.ok(new ProductLangResponse(idProduct, languageCode, data))
				.links(self).build();
	}

	/**
	 * CREATE BY LANG
	 */
	@Override
	public Response createProductLang(final String languageCode, final ProductLangData data) {
		Long newId = manager.createProductLang(LanguageCode.getByCode(languageCode), data);

		final URI uri = uriInfo.getAbsolutePathBuilder().path(newId.toString()).build();
		return Response.created(uri).language(Locale.FRENCH).build();
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
	public Response putProductLang(final String languageCode, final Long productId, final ProductLangData data) {
		manager.updateProductLang(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}
	
	@Override
	public Response getCategories(String languageCode, Long productId) 
	{		
		return Response.noContent().build();
	}

}