package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductBasicRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductBasicData;
import fr.webmaker.microservices.catalog.products.id.ProductID;
import lombok.Getter;

/**
 * Backing Bean pour administration des données de base du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductBasicMB")
@ViewScoped
public class ProductBasicMB extends AbstractProductMB<ProductBasicData, ProductID> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductBasicRestClient service;

	

	@Override
	public SingleResponse<ProductBasicData, ProductID> callServiceFindById(String identifier) {
		return service.get("fr", Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		service.patch("fr", model.getIdentifier().getId(), model.getData());
	}

	@Override
	protected void callServiceCreate() {
//		service.create("fr", getModel().getData());
	}

	@Override
	protected void callServiceDelete(Long id) {
		// Ignore (Non applicable)
	}
	
	@Override
	FlowPage getFlowPage() {
		return  FlowPage.BASIC;
	}
	
	@Override
	public ProductID newIdentifier(){
		return new ProductID(null, null);
	}

}
