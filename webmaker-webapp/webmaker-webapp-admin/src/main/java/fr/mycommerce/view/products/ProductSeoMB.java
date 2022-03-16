package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductSeoRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductSeoData;
import lombok.Getter;

/**
 * Backing Bean pour administration des données de base du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductSeoMB")
@ViewScoped
public class ProductSeoMB extends AbstractProductMB<ProductSeoData> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductSeoRestClient service;

	@Override
	public byte[] callServiceFindById(String identifier) {
		return service.get("fr", Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		model.getData();
		service.patch("fr",Long.valueOf(model.getIdentifier()), null);
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
		// TODO Auto-generated method stub
		return  FlowPage.SEO;
	}

}
