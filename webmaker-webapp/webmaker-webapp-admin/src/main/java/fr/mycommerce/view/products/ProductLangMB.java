package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductBasicRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductLangCompositeData;
import lombok.Getter;

/**
 * Backing Bean pour l'administration des données de base du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductBasicMB")
@ViewScoped
public class ProductLangMB extends AbstractProductMB<ProductLangCompositeData> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business (RestFul JSON::API)
	 */
	@Inject
	@RestClient
	@Getter
	private ProductBasicRestClient service;

	public ProductLangMB() {
		super(FlowPage.BASIC);
	}

	@Override
	public byte[] callServiceFindById(String identifier) {
		
		try {
			return service.get("fr", Long.valueOf(identifier));
		} catch (javax.ws.rs.WebApplicationException e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public void callServiceUpdate() {
		service.patch("fr", Long.valueOf(model.getIdentifier()), writeDocument(model.getData()));
	}

	@Override
	protected void callServiceCreate() {
//		service.create("fr", getModel().getData());
	}

	@Override
	protected void callServiceDelete(Long id) {
		// Ignore (Non applicable)
	}

}
