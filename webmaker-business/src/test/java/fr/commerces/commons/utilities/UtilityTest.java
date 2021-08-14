package fr.commerces.commons.utilities;

import fr.commerces.microservices.product.ProductResourceApi;
import fr.commerces.microservices.product.basic.ProductBasicResourceApi;
import fr.commerces.microservices.product.deliveries.ProductShippingApi;
import fr.commerces.microservices.product.pricing.ProductPricingApi;
import fr.commerces.microservices.product.seo.ProductSeoApi;
import fr.commerces.microservices.product.stocks.ProductStockApi;
import fr.commerces.microservices.product.variations.ProductVariationApi;
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

	public final String LANG_CODE_BIDON = "xx";
	public final String LANG_CODE_FR = "fr";

	public final long PRODUCT_ID_BIDON = -1L;
	public final long PRODUCT_ID_10000001 = 10000001L;
	
	public final long CLIENT_BIDON = -1L;
	public final long COMMANDE_BIDON = -1L;
	
}