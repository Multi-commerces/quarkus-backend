package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductStockRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductStockData;
import lombok.Getter;

/**
 * Backing Bean pour administration des données de stock du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductStockMB")
@ViewScoped
public class ProductStockMB extends AbstractProductMB<ProductStockData> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductStockRestClient service;
	
	public ProductStockMB() {
		super(FlowPage.STOCK);
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
