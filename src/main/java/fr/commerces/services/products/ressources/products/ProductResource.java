package fr.commerces.services.products.ressources.products;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.hypermedia.Hypermedia;
import fr.commerces.hypermedia.HypermediaLink;
import fr.commerces.hypermedia.HypermediaSelf;
import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductData;
import fr.commerces.services.products.ressources.deliveries.ProductDeliveryResource;
import fr.commerces.services.products.ressources.pricing.ProductPricingResource;
import fr.commerces.services.products.ressources.seo.ProductSeoResource;
import fr.commerces.services.products.ressources.stock.ProductStockResource;
import fr.commerces.services.products.ressources.variations.ProductVariationApi;


@HypermediaSelf(resource = ProductResourceApi.class, title = "Informations basiques du produit", 
links = {
		@HypermediaLink(resource = ProductSeoResource.class, title = "Informations du produit pour le référencement SEO"),
		@HypermediaLink(resource = ProductStockResource.class, title = "Stock du produit"),
		@HypermediaLink(resource = ProductPricingResource.class, title = "Prix du produit"),
		@HypermediaLink(resource = ProductDeliveryResource.class, title = "Mode de livraison du produit"),
		@HypermediaLink(resource = ProductVariationApi.class, title = "Les différentes déclinaisons du produit") })
@RequestScoped
public class ProductResource extends GenericResource<GenericResponse<ProductData, Long>> implements ProductResourceApi {

	@Inject
	ProductManager manager;
	
	@Context HttpHeaders headers;

	@Hypermedia
	@Override
	public GenericResponse<ProductData, Long> getProductById(final String languageCode, final Long idProduct) {
		return manager.findByIdProductAndLanguageCode(idProduct, LanguageCode.getByCode(languageCode));
	}

	@Hypermedia
	@Override
	public Collection<GenericResponse<ProductData, Long>> getProducts(final String languageCode,
			final Integer page, final Integer size) {
		return manager.list(LanguageCode.getByCode(languageCode), Optional.ofNullable(page), Optional.ofNullable(size));
	}

	@Override
	public Response createProduct(final String languageCode, final ProductData data) {
		Long newId = manager.create(LanguageCode.getByCode(languageCode), data);
		return buildResponse.apply(newId);
	}

	@Override
	public Response updateProduct(Long productId, ProductData data) {
		manager.update(productId, data);
		return Response.noContent().build();
	}

}