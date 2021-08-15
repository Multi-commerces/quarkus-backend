package fr.webmaker.view;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.webmaker.microservices.catalog.products.data.ProductData;
import fr.webmaker.microservices.catalog.products.id.ProductID;
import fr.webmaker.microservices.catalog.products.response.ProductResponse;
import fr.webmaker.resource.ProductRestClient;
import lombok.Getter;

@ViewScoped
@Named
public class FicheProductMB implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	@Getter
	private ProductRestClient service;
	
	@Getter
	private ProductID identifier;

	@Getter
	private ProductData product;
	
	
	@Getter
	private Map<ProductID, ProductData> similarProduct;

	@PostConstruct
	public void init() {
		var productId = getValueParam("productId");
		ProductResponse response = service.get("fr", Long.valueOf(productId));
		product = response.getData();
		identifier = response.getIdentifier();
	}
	
	protected String getValueParam(String param) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// la demande n'est pas une publication:
		if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
			// extraire l'id de la chaîne de requête
			Map<String, String> paramMap = facesContext.getExternalContext().getRequestParameterMap();
			return paramMap.get(param);
		}

		return null;
	}

}
