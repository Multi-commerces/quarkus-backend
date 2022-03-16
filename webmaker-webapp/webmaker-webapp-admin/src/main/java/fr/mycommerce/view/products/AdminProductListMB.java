package fr.mycommerce.view.products;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import com.github.jasminb.jsonapi.ResourceConverter;

import fr.mycommerce.commons.managers.Manager;
import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.mycommerce.commons.views.ActionType;
import fr.mycommerce.service.product.ProductRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductCompositeData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("adminProductMB")
@ViewScoped
public class AdminProductListMB extends AbstractCrudView<ProductCompositeData>
		implements Manager<ProductCompositeData> {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	@Getter
	private ProductRestClient service;

	@Inject
	@Getter
	private ProductVariationMB variationMB;


	@Getter
	@Setter
	protected int activeIndexTabMenu = 0;

	/**
	 * Action : changement du menu active
	 * 
	 * @param event
	 */
	public void tabMenuAction(ActionEvent event) {
		String value = (String) event.getComponent().getAttributes().get("activeIndexTabMenu");
		this.activeIndexTabMenu = value != null ? Integer.valueOf(value) : -1;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
		myNav.handleNavigation(facesContext, null, FlowPage.BASIC.getPage());
	}

	public String showPage() {
		final FlowPage flowProductPage = FlowPage.stream().parallel()
				.filter(o -> o.getTabNUm().equals(activeIndexTabMenu)).findAny().orElse(FlowPage.BASIC);

		return flowProductPage.getPage() + "?faces-redirect=true&id=" + model.getIdentifier();
	}

	@Override
	public void reset(ActionEvent event) {
		if (action == ActionType.UPDATE || action == ActionType.CREATE) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
			myNav.handleNavigation(facesContext, null, FlowPage.BASIC.getPage() + "?faces-redirect=true");
		}

		super.reset();
	}

	@Override
	public void onRowDblSelect(SelectEvent<Model<ProductCompositeData>> event) {
		super.onRowDblSelect(event);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
		myNav.handleNavigation(facesContext, null,
				FlowPage.BASIC.getPage() + "?faces-redirect=true&id=" + model.getIdentifier());
	}

	public void onRowSelect(SelectEvent<Model<ProductCompositeData>> event) {
		model = event.getObject();
		//variationMB.loadByProductId(event.getObject().getIdentifier().getId());
	}

	@Override
	public void editAction(ActionEvent event) {
		super.editAction(event);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
		myNav.handleNavigation(facesContext, null,
				FlowPage.BASIC.getPage() + "?faces-redirect=true&id=" + model.getIdentifier());
	}

	/* *****************************************************************************************
	 * **************************************** ACTIONS ****************************************
	 * *****************************************************************************************/

	@Override
	public List<Model<ProductCompositeData>> findAll() {
		byte[] flux = service.getProducts("fr", 1, 10);
		List<ProductCompositeData> collection = new ResourceConverter(objectMapper, ProductCompositeData.class)
			.readDocumentCollection(flux, ProductCompositeData.class).get();

		return collection.stream()
				.map(o -> new Model<ProductCompositeData>(o))
				.collect(Collectors.toList());
	}

	@Override
	public void create() {
		getModel().getData();
		Response response = service.create("fr", null);
		if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
			String location = response.getHeaders().getFirst("location").toString();
			response.close();

			try {
				final Client client = ClientBuilder.newClient();
				Response responseGet = client.target(location).request().get();
				if (responseGet.getStatus() == Response.Status.OK.getStatusCode()) {
					
					
//					final ProductLangResponse dataResponse = null;
//							responseGet.readEntity(ProductLangResponse.class);

					responseGet.close();
					client.close();

					items.add(new Model<ProductCompositeData>(null));
				}
			} catch (Exception e) {
				log.warn("impossible de récupérer item nouvellement créé, vérifier que le service est up");
				return;
			}

		}
	}

	@Override
	public void update() {
//		getModel().getData();
		service.update("fr", Long.valueOf(model.getIdentifier()), null);
	}

	@Override
	public void delete(String identifier) {
		service.delete("fr", Long.valueOf(model.getIdentifier()));
	}

	@Override
	protected ProductCompositeData newDataInstance() {
		return new ProductCompositeData();
	}

	@Override
	protected void delete(List<String> identifiers) {
		identifiers.stream().forEach(identifier -> service.delete("fr", Long.valueOf(identifier)));

	}

}
