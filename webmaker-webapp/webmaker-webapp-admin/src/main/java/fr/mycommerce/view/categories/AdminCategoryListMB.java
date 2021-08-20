package fr.mycommerce.view.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.Manager;
import fr.mycommerce.service.categories.CategoryRestClient;
import fr.mycommerce.transverse.AbstractCrudView;
import fr.mycommerce.transverse.Model;
import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.response.CategoryHierarchyCollectionResponse;
import lombok.Getter;

@Named("adminCategoryMB")
@ViewScoped
public class AdminCategoryListMB extends AbstractCrudView<CategoryData, LongID>
		implements Manager<CategoryData, LongID> {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	@Getter
	private CategoryRestClient service;


	@Override
	public List<Model<CategoryData, LongID>> findAll() {


		final CategoryHierarchyCollectionResponse response = service.getCategories("fr", true);
		if (response.get_embedded() == null) {
			return new ArrayList<Model<CategoryData, LongID>>();
		}

		return service.getCategories("fr", true).get_embedded().stream()
				.map(o -> new Model<CategoryData, LongID>(o.getIdentifier(), o.getData()))
				.collect(Collectors.toList());
	}

	@Override
	public void create() {

	}

	@Override
	public void update() {

	}

	@Override
	public void delete(LongID identifier) {

	}

	@Override
	protected CategoryData newDataInstance() {
		// TODO Auto-generated method stub
		return new CategoryData();
	}



}
