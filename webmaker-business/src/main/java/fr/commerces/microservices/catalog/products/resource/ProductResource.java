package fr.commerces.microservices.catalog.products.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.hypermedia.HypermediaApi;
import fr.commerces.commons.hypermedia.HypermediaLink;
import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.commerces.microservices.catalog.products.manager.ProductLangManager;
import fr.commerces.microservices.catalog.products.openapi.ProductBasicResourceApi;
import fr.commerces.microservices.catalog.products.openapi.ProductPricingApi;
import fr.commerces.microservices.catalog.products.openapi.ProductResourceApi;
import fr.commerces.microservices.catalog.products.openapi.ProductSeoApi;
import fr.commerces.microservices.catalog.products.openapi.ProductShippingApi;
import fr.commerces.microservices.catalog.products.openapi.ProductStockApi;
import fr.commerces.microservices.catalog.products.openapi.ProductVariationApi;
import fr.webmaker.commons.data.LinkData;
import fr.webmaker.commons.data.RelationData;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.commons.request.PageRequest;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryLangData;
import fr.webmaker.microservices.catalog.products.data.ProductLangData;
import fr.webmaker.microservices.catalog.products.response.ProductLangResponse;
import lombok.Getter;

@HypermediaApi(resource = ProductBasicResourceApi.class, 
	title = "Informations sur les produits", 
	links = {
		@HypermediaLink(resource = ProductSeoApi.class, methode = "getProductSeo", rel = "seo"),
		@HypermediaLink(resource = ProductStockApi.class, methode = "getProductStock", rel = "stocks"),
		@HypermediaLink(resource = ProductPricingApi.class, methode = "getProductShipping", rel = "shippings"),
		@HypermediaLink(resource = ProductShippingApi.class, methode = "getProductShipping", rel = "shippings"),
		@HypermediaLink(resource = ProductVariationApi.class, methode = "getVariations", rel = "variations"),
	})
@RequestScoped
public class ProductResource extends GenericResource implements ProductResourceApi {

	@Inject
	ProductLangManager manager;
	
	@Inject
	CategoryManager categoryManager;
	
	@Context @Getter
	UriInfo uriInfo;
	
	private void initActionsAndLinks(ProductLangResponse response) {
	    long id = response.getIdentifier().getId();  
		
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
	    		  .path(Long.toString(id));
	      
	      Link delete = Link.fromUriBuilder(uriBuilder)
	    		  .rel("delete")
	    		  .title("Suppression de la ressource")
	    		  .param("method", "DELETE").build();
	      
	      Link update = Link.fromUriBuilder(uriBuilder)
	    		  .rel("update")
	    		  .title("Modification complète de la ressource")
	    		  .type("UPDATE").build();
	      
	      
	     
	      final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
	      
	      response.getLinks().add(new LinkData(uri.getPath()));
	  }

	/**
	 * GET ALL
	 */
//	@Hypermedia
	@Override
	public CollectionResponse<ProductLangData, LangID> getProducts(final String languageCode, final PageRequest page) {
		LanguageCode language = LanguageCode.getByCode(languageCode);
		Optional<Integer> _size = Optional.ofNullable(page.getSize());
		Optional<Integer> _page = Optional.ofNullable(page.getPage());

		// Recherche des produits
		final Map<Long, ProductLangData> productByProductId = manager.findAllByLanguageCode(language, _page, _size);
		
		// Recherche des catégories des produits
		final Map<Long, List<SingleCompositeData<CategoryLangData, LongID>>> categoriesByProductId = categoryManager.findCategoriesByProductIds2(productByProductId.keySet());
		Map<LongID, CategoryLangData> catIncluded = new HashMap<>();
	
		
		
		/*
		 * Collection
		 */
		final List<SingleCompositeData<ProductLangData, LangID>> collection = productByProductId.entrySet().stream()
				// map (encapsulation dans un ProductResponse)
				.map(productLangEntry ->  {
					final Long productId = productLangEntry.getKey();
					final ProductLangData productLangData = productLangEntry.getValue();
					
					final List<LongID> catRelations = new ArrayList<>();
					for (SingleCompositeData<CategoryLangData, LongID> category : categoriesByProductId.get(productId)) {		
						catRelations.add(category.getIdentifier());
						catIncluded.put(category.getIdentifier(), category.getData());
					}
					
					/* Relationships */
					final Map<String, RelationData> relationships = new HashMap<>();
					
					// Catégories
					var uri = uriInfo.getAbsolutePathBuilder()
							.path(Long.toString(productId))
							.path("/categories").build();
					relationships.put("categories", new RelationData(uri.getPath(), catRelations));

					return ProductLangResponse.<ProductLangData, LangID>builder()
						.identifier(new LangID(productId, language))
						.data(productLangData)
						.relationships(relationships)
						.build();
				})
				// collect
				.collect(Collectors.toList());

		/*
		 * CollectionResponse
		 */
		var col =  CollectionResponse.<ProductLangData, LangID>builder(uriInfo)
			// collection
			.collection(collection)
			// paging
			.paging(manager.getPagingProductLang(language, _page, _size))
			
			.build();
		

		// Included
		col.getIncluded().put("categories", 
			catIncluded.entrySet().stream()
				.map(o -> new SingleCompositeData<>("categories", o.getKey(), o.getValue()))
				.collect(Collectors.toList()));
		
		return col;
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
	public Response patchProductLang(final String languageCode, final Long productId, final ProductLangData data) {
		manager.updateProductLang(LanguageCode.getByCode(languageCode), productId, data);
		return Response.noContent().build();
	}
	
	
	@Override
	public CollectionResponse<CategoryLangData, LongID> getCategories(Long productId) {
		final Map<Long, List<SingleCompositeData<CategoryLangData, LongID>>> categoriesByProductId = categoryManager
				.findCategoriesByProductIds2(Arrays.asList(productId));
		
		/* Relationships */
		final Map<String, RelationData> relationships = new HashMap<>();
		
		// Catégories
		var uri = uriInfo.getAbsolutePathBuilder()
				.path("/languages")
				.path(Long.toString(productId))
				.build();
		
		return CollectionResponse.<CategoryLangData, LongID>builder(uriInfo)
				.collection(categoriesByProductId.get(productId)).build();
	}

}