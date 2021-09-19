package fr.mycommerce.view.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.commons.managers.Manager;
import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.restapi.CategoryRestClient;
import lombok.Getter;

@Named("adminCategoryMB")
@ViewScoped
public class AdminCategoryListMB extends AbstractCrudView<CategoryData, LangID>
		implements Manager<CategoryData, LangID> {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	@Getter
	private CategoryRestClient service;

	@Override
	public List<Model<CategoryData, LangID>> findAll() {
		final CollectionResponse<CategoryData, LangID> response = service.getCategories("fr", true);
		if (response.getCollection() == null) {
			return new ArrayList<Model<CategoryData, LangID>>();
		}

		return service.getCategories("fr", true).getCollection().stream()
				.map(o -> new Model<CategoryData, LangID>(o.getIdentifier(), o.getData())).collect(Collectors.toList());
	}

	@Override
	protected CategoryData newDataInstance() {
		return new CategoryData();
	}

	@Override
	public void create() {

	}

	@Override
	public void update() {

	}

	@Override
	public void delete(LangID identifier) {

	}

	@Override
	protected void delete(List<LangID> ids) {

	}

}