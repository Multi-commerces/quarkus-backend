package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.commons.tools.RestTool;
import fr.mycommerce.service.product.ProductPricingRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductPricingData;

/**
 * Backing Bean pour administration des données de stock du produit
 * 
 * @author Julien ILARI
 *
 */
@ViewScoped
@Named("adminProductPricingMB")
public class ProductPricingMB extends AbstractProductMB<ProductPricingData> {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	private ProductPricingRestClient service;

	public ProductPricingMB() {
		super(FlowPage.PRICING);
	}

	@Override
	public byte[] callServiceFindById(String identifier) {
		return service.get(Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		service.patch(Long.valueOf(model.getIdentifier()), RestTool.writeDocument(model.getData()));
	}

}
