package fr.commerces.microservices.catalog.products.self.model;

import fr.webmaker.restfull.hateos.IInclusion;

public enum ProductInclude implements IInclusion {

	CATEGORIES("categories"), 
	IMAGES("image"), 
	LANGS("productLangs"), 
	PRICING("pricing"),
	SHIPPING("shipping"), 
	STOCKS("stock"), 
	VARIATIONS("variation");

	private String type;

	ProductInclude(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return type;
	}

}
