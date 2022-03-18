package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductShippingRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductShippingData;
import lombok.Getter;

/**
 * Backing Bean Administration Shipping
 * @author Julien ILARI
 *
 */
@Named("adminProductShippingMB")
@ViewScoped
public class ProductShippingMB extends AbstractProductMB<ProductShippingData> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductShippingRestClient service;
	
	public ProductShippingMB() {
		super(FlowPage.SHIPPING);
	}
	
	@Override
	public byte[] callServiceFindById(String identifier) {
		return service.get(Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		service.patch(Long.valueOf(model.getIdentifier()), writeDocument(model.getData()));
	}

	@Override
	protected void callServiceCreate() {
		// Ignore (Non applicable)
	}

	@Override
	protected void callServiceDelete(Long id) {
		// Ignore (Non applicable)
	}

}
