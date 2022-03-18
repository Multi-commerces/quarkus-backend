package fr.mycommerce.view.products;

import java.io.InputStream;
import java.util.Arrays;
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

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.mycommerce.commons.managers.Manager;
import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.mycommerce.commons.views.ActionType;
import fr.mycommerce.service.product.ProductRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.data.product.ProductLangData;
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
	
	@Getter
	@Setter
	private ProductLangData langData = new ProductLangData();
	
	public ProductLangData getProductLang()
	{	
		return model.getData().getProductLangs().stream()
				.filter(p -> p.getLanguageCode() == LanguageCode.fr)
				.findAny()
				.orElse(new ProductLangData());
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
	public void onRowDblSelect(SelectEvent<Model<ProductCompositeData>> event) {
		super.onRowDblSelect(event);

		handleNavigation(FlowPage.BASIC.getPage());
	}

	public void onRowSelect(SelectEvent<Model<ProductCompositeData>> event) {
		model = event.getObject();
		variationMB.loadByProductId(event.getObject().getIdentifier());
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
	public List<Model<ProductCompositeData>> findAll() {
		byte[] flux = service.getProducts(1, 10);
		List<ProductCompositeData> collection = new ResourceConverter(objectMapper, ProductCompositeData.class)
			.readDocumentCollection(flux, ProductCompositeData.class).get();

		return collection.stream()
				.map(o -> new Model<ProductCompositeData>(o))
				.collect(Collectors.toList());
	}

	@Override
	public void create() {
		ResourceConverter converter = new ResourceConverter(objectMapper, ProductData.class, ProductCompositeData.class);
		ProductCompositeData data = getModel().getData();
		data.setId("-1");
		// Get response data
		byte [] rawResponse = null;
		try {
			rawResponse = converter.writeDocument(new JSONAPIDocument<ProductData>(data, objectMapper));
		} catch (DocumentSerializationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		Response response = service.create(rawResponse);
		if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
			String location = response.getHeaders().getFirst("location").toString() + "?relationships=CATEGORIES&relationships=LANGS&relationships=VARIATIONS";
			response.close();

			try {
				final Client client = ClientBuilder.newClient();
				Response responseGet = client.target(location)
						.request().accept("application/vnd.api+json")
						.get();
				if (responseGet.getStatus() == Response.Status.OK.getStatusCode()) {
					
					InputStream dataResponse = responseGet.readEntity(InputStream.class);

					data = converter.readDocument(dataResponse, ProductCompositeData.class).get();

					responseGet.close();
					client.close();

					items.add(new Model<ProductCompositeData>(data));
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
		service.update(model.getIdentifier(), null);
	}

	@Override
	public void delete(String identifier) {
		service.delete(model.getIdentifier());
	}

	@Override
	protected ProductCompositeData newDataInstance() {
		ProductCompositeData data = new ProductCompositeData();
		data.setId("-1");

		// Langue par défaut du produit sera FR
		ProductLangData value = new ProductLangData();
		value.setId("-1");
		value.setLanguageCode(LanguageCode.fr);
		data.setProductLangs(Arrays.asList(value));
		
		
		return data;
	}

	@Override
	protected void delete(List<String> identifiers) {
		identifiers.stream().forEach(identifier -> service.delete(identifier));

	}

}
