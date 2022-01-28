package fr.commerces.microservices.catalog.categories.data;

import fr.webmaker.data.category.CategoryData;
import fr.webmaker.restfull.hateos.schema.IShemaData;

public interface ShemaCreateCategoryData {

	IShemaData<CategoryData> getData();
}
