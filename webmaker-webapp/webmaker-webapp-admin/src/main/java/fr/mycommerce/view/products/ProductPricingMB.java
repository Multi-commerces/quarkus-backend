package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductPricingRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductPricingData;
import lombok.Getter;

/**
 * Backing Bean pour administration des données de stock du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductPricingMB")
@ViewScoped
public class ProductPricingMB extends AbstractProductMB<ProductPricingData> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductPricingRestClient service;
	
	@Override
	public byte[] callServiceFindById(String identifier) {
		return service.get(Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		//model.getData()
		service.patch(Long.valueOf(model.getIdentifier()), null);
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
		return FlowPage.PRICING;
	}

}
