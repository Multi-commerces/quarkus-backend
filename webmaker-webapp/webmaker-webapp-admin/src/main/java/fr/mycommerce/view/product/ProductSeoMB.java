package fr.mycommerce.view.product;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.service.product.ProductSeoRestClient;
import fr.mycommerce.view.product.ProductFlowPage.FlowPage;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.data.ProductSeoData;
import lombok.Getter;

/**
 * Backing Bean pour administration des données de base du produit
 * @author Julien ILARI
 *
 */
@Named("adminProductSeoMB")
@ViewScoped
public class ProductSeoMB extends AbstractProductMB<ProductSeoData, Identifier<Long>> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductSeoRestClient service;

	@Override
	public SingleResponse<ProductSeoData, Identifier<Long>> callServiceFindById(String identifier) {
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
		// TODO Auto-generated method stub
		return  FlowPage.SEO;
	}

	@Override
	public Identifier<Long> newIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

}
