package fr.commerces.microservices.catalog.categories.data;

import java.util.List;

import fr.webmaker.data.category.CategoryData;
import fr.webmaker.restfull.hateos.schema.IShemaData;

public interface ShemaCategoryData {

	List<IShemaData<CategoryData>> getData();
	
}
