package fr.mycommerce.view.product;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductShippingRestClient;
import fr.mycommerce.view.product.ProductFlowPage.FlowPage;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.data.ProductShippingData;
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
