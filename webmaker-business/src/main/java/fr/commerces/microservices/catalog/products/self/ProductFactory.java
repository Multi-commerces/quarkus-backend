package fr.commerces.microservices.catalog.products.self;

import javax.enterprise.context.ApplicationScoped;

import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;

@ApplicationScoped
public class ProductFactory {

	public ProductCompositeData newCompositeData() {
		return new ProductCompositeData();
	}

	public ProductData newData() {
		return new ProductData();
	}
}