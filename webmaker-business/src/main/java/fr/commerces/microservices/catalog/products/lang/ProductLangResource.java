package fr.commerces.microservices.catalog.products.lang;

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

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.id.ProductLangID;
import lombok.Getter;

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
	@Override
	public CollectionResponse<ProductLangData, ProductLangID> getProducts(Long productId) {
		// Recherche des produits
		Map<LanguageCode, ProductLangData> productByProductId = null;
		
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
		final ProductLangData data = manager.findByPK(idProduct, LanguageCode.getByCode(languageCode));
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