package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductShippingData;
import fr.webmaker.microservices.catalog.products.restclient.ProductShippingRestClient;
import lombok.Getter;

/**
 * Backing Bean Administration Shipping
 * @author Julien ILARI
 *
 */
@Named("adminProductShippingMB")
@ViewScoped
public class ProductShippingMB extends AbstractProductMB<ProductShippingData, Identifier<Long>> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductShippingRestClient service;
	
	@Override
	public SingleResponse<ProductShippingData, Identifier<Long>> callServiceFindById(String identifier) {
		return service.get(Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		service.patch(model.getIdentifier().getId(), model.getData());
	}

	@Override
	protected void callServiceCreate() {
		// Ignore (Non applicable)
	}

	@Override
	protected void callServiceDelete(Long id) {
		// Ignore (Non applicable)
	}

	@Override
	FlowPage getFlowPage() {
		return FlowPage.SHIPPING;
	}

	@Override
	public Identifier<Long> newIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

}
