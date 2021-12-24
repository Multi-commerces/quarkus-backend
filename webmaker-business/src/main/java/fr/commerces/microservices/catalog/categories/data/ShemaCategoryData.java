package fr.commerces.microservices.catalog.categories.data;

import java.util.List;

import fr.commerces.commons.resources.ShemaData;

public interface ShemaCategoryData {

	List<ShemaData<CategoryData>> getData();
	
}
