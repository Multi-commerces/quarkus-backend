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

import fr.mycommerce.commons.managers.Manager;
import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.mycommerce.commons.views.ActionType;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductLangData;
import fr.webmaker.microservices.catalog.products.id.ProductLangID;
import fr.webmaker.microservices.catalog.products.response.ProductLangResponse;
import fr.webmaker.microservices.catalog.products.restclient.ProductRestClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("adminProductMB")
@ViewScoped
public class AdminProductListMB extends AbstractCrudView<ProductLangData, ProductLangID>
		implements Manager<ProductLangData, ProductLangID> {

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
	public void onRowDblSelect(SelectEvent<Model<ProductLangData, ProductLangID>> event) {
		super.onRowDblSelect(event);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
		myNav.handleNavigation(facesContext, null,
				FlowPage.BASIC.getPage() + "?faces-redirect=true&id=" + model.getIdentifier().getId());
	}

	public void onRowSelect(SelectEvent<Model<ProductLangData, ProductLangID>> event) {
		model = event.getObject();
		//variationMB.loadByProductId(event.getObject().getIdentifier().getId());
	}

	@Override
	public void editAction(ActionEvent event) {
		super.editAction(event);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
		myNav.handleNavigation(facesContext, null,
				FlowPage.BASIC.getPage() + "?faces-redirect=true&id=" + model.getIdentifier().getId());
	}

	/* *****************************************************************************************
	 * **************************************** ACTIONS ****************************************
	 * *****************************************************************************************/

	@Override
	public List<Model<ProductLangData, ProductLangID>> findAll() {
		return service.getProducts("fr", 1, 10).getCollection().stream()
				.map(o -> new Model<ProductLangData, ProductLangID>(o.getIdentifier(), o.getData()))
				.collect(Collectors.toList());
	}

	@Override
	public void create() {
		Response response = service.create("fr", getModel().getData());
		if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
			String location = response.getHeaders().getFirst("location").toString();
			response.close();

			try {
				final Client client = ClientBuilder.newClient();
				Response responseGet = client.target(location).request().get();
				if (responseGet.getStatus() == Response.Status.OK.getStatusCode()) {
					final ProductLangResponse dataResponse = responseGet.readEntity(ProductLangResponse.class);

					responseGet.close();
					client.close();

					items.add(new Model<ProductLangData, ProductLangID>(dataResponse.getIdentifier(), dataResponse.getData()));
				}
			} catch (Exception e) {
				log.warn("impossible de récupérer item nouvellement créé, vérifier que le service est up");
				return;
			}

		}
	}

	@Override
	public void update() {
		service.update("fr", model.getIdentifier().getId(), getModel().getData());
	}

	@Override
	public void delete(ProductLangID identifier) {
		service.delete(identifier.getLanguageCode(), identifier.getId());
	}

	@Override
	protected ProductLangData newDataInstance() {
		return new ProductLangData();
	}

	@Override
	protected void delete(List<ProductLangID> identifiers) {
		identifiers.stream().forEach(identifier -> service.delete(identifier.getLanguageCode(), identifier.getId()));

	}

}
