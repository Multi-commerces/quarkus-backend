package fr.commerces.common;

import fr.commerces.services.internal.products.ressources.basic.ProductBasicResourceApi;
import fr.commerces.services.internal.products.ressources.deliveries.ProductShippingApi;
import fr.commerces.services.internal.products.ressources.pricings.ProductPricingApi;
import fr.commerces.services.internal.products.ressources.products.ProductResourceApi;
import fr.commerces.services.internal.products.ressources.seo.ProductSeoApi;
import fr.commerces.services.internal.products.ressources.stocks.ProductStockApi;
import fr.commerces.services.internal.products.ressources.variations.ProductVariationApi;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilityTest {

	public final String PATH_PRODUCT = ProductResourceApi.PATH;
	public final String PATH_PRODUCT_BASIC = ProductBasicResourceApi.PATH;
	public final String PATH_PRODUCT_SHIPPING = ProductShippingApi.PATH;
	public final String PATH_PRODUCT_PRICING = ProductPricingApi.PATH;
	public final String PATH_PRODUCT_SEO = ProductSeoApi.PATH;
	public final String PATH_PRODUCT_STOCK = ProductStockApi.PATH;
	public final String PATH_PRODUCT_VARIATION = ProductVariationApi.PATH;

	public final String LANG_FR = "fr";

	public final long PRODUCT_BIDON = -1L;
	public final long PRODUCT_10000001 = 10000001L;

}