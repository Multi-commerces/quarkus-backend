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

import fr.mycommerce.service.Manager;
import fr.mycommerce.service.product.ProductRestClient;
import fr.mycommerce.transverse.AbstractCrudView;
import fr.mycommerce.transverse.ActionType;
import fr.mycommerce.transverse.Model;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.microservices.catalog.products.data.ProductData;
import fr.webmaker.microservices.catalog.products.id.ProductID;
import fr.webmaker.microservices.catalog.products.response.ProductResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("adminProductMB")
@ViewScoped
public class AdminProductListMB extends AbstractCrudView<ProductData, ProductID>
		implements Manager<ProductData, ProductID> {

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
	public void onRowDblSelect(SelectEvent<Model<ProductData, ProductID>> event) {
		super.onRowDblSelect(event);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
		myNav.handleNavigation(facesContext, null,
				FlowPage.BASIC.getPage() + "?faces-redirect=true&id=" + model.getIdentifier().getId());
	}

	public void onRowSelect(SelectEvent<Model<ProductData, ProductID>> event) {
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
	public List<Model<ProductData, ProductID>> findAll() {
		return service.getProducts("fr", 1, 10).getCollection().stream()
				.map(o -> new Model<ProductData, ProductID>(o.getIdentifier(), o.getData()))
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
					final ProductResponse dataResponse = responseGet.readEntity(ProductResponse.class);

					responseGet.close();
					client.close();

					items.add(new Model<ProductData, ProductID>(dataResponse.getIdentifier(), dataResponse.getData()));
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
	public void delete(ProductID identifier) {
		service.delete(identifier.getLanguageCode(), identifier.getId());
	}

	@Override
	protected ProductData newDataInstance() {
		return new ProductData();
	}

	@Override
	protected void delete(List<ProductID> identifiers) {
		identifiers.stream().forEach(identifier -> service.delete(identifier.getLanguageCode(), identifier.getId()));

	}

}
