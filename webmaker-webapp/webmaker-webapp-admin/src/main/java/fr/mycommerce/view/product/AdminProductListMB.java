package fr.mycommerce.view.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
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
import fr.mycommerce.view.product.ProductFlowPage.FlowPage;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductData;
import fr.webmaker.product.response.ProductDataResponse;
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
	private String selectedImage;

	@Getter
	@Setter
	private List<String> images = new ArrayList<>();

	@Getter
	@Setter
	protected int activeIndexTabMenu = 0;

	@PostConstruct
	public void postConstruct() {
		for (int i = 1; i <= 12; i++) {
			images.add("nature" + i + ".jpg");
		}
	}

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

	public String getImage() {
		return getImage(getBigPicture());
	}

	public byte[] getBigPicture() {

		if (action == ActionType.CREATE && uploadedFile != null && uploadedFile.getContent() != null) {
			return uploadedFile.getContent();
		}

		if (action == ActionType.UPDATE && uploadedFile != null && uploadedFile.getContent() != null) {
			return uploadedFile.getContent();
		}

		return null;

	}

	@Override
	public List<Model<ProductData, ProductID>> findAll() {
		return service.getProducts("fr", 1, 10).get_embedded().stream()
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
					final ProductDataResponse dataResponse = responseGet.readEntity(ProductDataResponse.class);

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

}
