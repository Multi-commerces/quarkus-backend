package fr.commerces.microservices.catalog.products.model;

import fr.webmaker.restfull.hateos.IInclusion;

public enum ProductRelation implements IInclusion {

	CATEGORIES("categories"), 
	IMAGES("image"), 
	LANGS("productLangs"), 
	PRICING("pricing"), 
	SEO("seo"),
	SHIPPING("shipping"), 
	STOCKS("stock"), 
	VARIATIONS("variation");

	private String type;

	ProductRelation(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return type;
	}

}
