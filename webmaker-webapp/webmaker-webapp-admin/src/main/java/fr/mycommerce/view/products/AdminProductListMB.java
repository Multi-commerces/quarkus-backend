package fr.mycommerce.view.products;

import java.io.InputStream;
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

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.tools.RestTool;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.mycommerce.commons.views.ActionType;
import fr.mycommerce.service.product.ProductRestClient;
import fr.mycommerce.service.product.ProductSheetRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductSheetData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("adminProductMB")
@ViewScoped
public class AdminProductListMB extends AbstractCrudView<ProductSheetData> {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	private ProductRestClient service;
	
	@Inject
	@RestClient
	private ProductSheetRestClient sheetService;

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
				.filter(o -> o.getTabNUm().equals(activeIndexTabMenu))
				.findAny()
				.orElse(FlowPage.BASIC);

		return flowProductPage.getPage() + "?faces-redirect=true&id=" + model.getIdentifier();
	}

	/**
	 * En mode mise à jour ou création, retour sur la liste des produits
	 */
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
	public void onRowDblSelect(SelectEvent<Model<ProductSheetData>> event) {
		super.onRowDblSelect(event);

		handleNavigation(FlowPage.BASIC.getPage());
	}

	public void onRowSelect(SelectEvent<Model<ProductSheetData>> event) {
		model = event.getObject();
		variationMB.loadByProductId(model.getId());
	}

	@Override
	public void editAction(ActionEvent event) {
		super.editAction(event);

		handleNavigation(FlowPage.BASIC.getPage());
	}

	/* *****************************************************************************************
	 * ************************************** ACTIONS CRUD *************************************
	 * *****************************************************************************************/

	@Override
	public List<Model<ProductSheetData>> findAll() {
		byte[] flux = sheetService.search();
		
		List<ProductSheetData> collection = RestTool.readDocumentCollection(flux, ProductSheetData.class).get();

		
		return collection.stream()
				.map(o -> new Model<ProductSheetData>(o))
				.collect(Collectors.toList());
	}

	@Override
	public void create() {
		ProductSheetData data = getModel().getData();
		// Get response data
		Response response = sheetService.create(RestTool.writeDocument(data));
		if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
		String location = response.getHeaders().getFirst("location").toString();
			response.close();

			try {
				final Client client = ClientBuilder.newClient();
				Response responseGet = client.target(location)
						.request().accept("application/vnd.api+json")
						.get();
				if (responseGet.getStatus() == Response.Status.OK.getStatusCode()) {
					
					InputStream dataResponse = responseGet.readEntity(InputStream.class);
					data = RestTool.readDocument(dataResponse, ProductSheetData.class).get();

					responseGet.close();
					client.close();

					items.add(new Model<ProductSheetData>(data));
				}
			} catch (Exception e) {
				log.warn("impossible de récupérer item nouvellement créé, vérifier que le service est up");
				return;
			}

		}
	}

	@Override
	public void update() {
		service.update(model.getIdentifier(), null);
	}

	@Override
	public void delete(String identifier) {
		service.delete(identifier);
	}

	@Override
	protected ProductSheetData newDataInstance() {
		ProductSheetData data = new ProductSheetData();
		
		return data;
	}

	@Override
	protected void delete(final List<String> identifiers) {
		identifiers.stream()
			.forEach(handlingConsumerWrapper(identifier -> service.delete(identifier), Exception.class));
	}

}
