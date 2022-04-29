package fr.mycommerce.view.categories;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.webmaker.data.category.CategoryCompositeData;

@Named("adminCategoryMB")
@ViewScoped
public class AdminCategoryListMB extends AbstractCrudView<CategoryCompositeData> {

	private static final long serialVersionUID = 1L;

//	@Inject
//	@RestClient
//	@Getter
//	private CategoryRestClient service;

	@Override
	public List<Model<CategoryCompositeData>> findAll() {
//		final CollectionResponse<CategoryData> response = service.getCategories("fr", true);
//		if (response.getCollection() == null) {
//			return new ArrayList<Model<CategoryData>>();
//		}
//
//		return service.getCategories("fr", true).getCollection().stream()
//				.map(o -> new Model<CategoryData, LangID>(o.getIdentifier(), o.getData())).collect(Collectors.toList());

		return Collections.emptyList();
	}

	@Override
	protected CategoryCompositeData newDataInstance() {
		return new CategoryCompositeData();
	}

	@Override
	public void create() {

	}

	@Override
	public void update() {

	}

	@Override
	public void delete(String identifier) {

	}
	
	@Override
	protected void delete(List<String> ids) {

		
	}

}
