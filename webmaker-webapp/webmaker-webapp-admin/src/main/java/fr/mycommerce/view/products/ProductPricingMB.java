package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductPricingData;
import fr.webmaker.microservices.catalog.products.restclient.ProductPricingRestClient;
import lombok.Getter;

/**
 * Backing Bean pour administration des données de stock du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductPricingMB")
@ViewScoped
public class ProductPricingMB extends AbstractProductMB<ProductPricingData, Identifier<Long>> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductPricingRestClient service;
	
	@Override
	public SingleResponse<ProductPricingData, Identifier<Long>> callServiceFindById(String identifier) {
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
		return FlowPage.PRICING;
	}

	@Override
	public Identifier<Long> newIdentifier() {
		return new Identifier<Long>();
	}

}
