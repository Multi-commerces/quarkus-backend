package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.commons.tools.RestTool;
import fr.mycommerce.service.product.ProductSeoRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductSeoData;
import lombok.Getter;

/**
 * Backing Bean pour administration des données seo du produit
 * 
 * @author Julien ILARI
 *
 */
@ViewScoped
@Named("adminProductSeoMB")
public class ProductSeoMB extends AbstractProductMB<ProductSeoData> {

	private static final long serialVersionUID = 1L;

	/**
	 * Service Business
	 */
	@Inject
	@RestClient
	@Getter
	private ProductSeoRestClient service;

	public ProductSeoMB() {
		super(FlowPage.SEO);
	}

	@Override
	public byte[] callServiceFindById(String identifier) {
		return service.get("fr", Long.valueOf(identifier));
	}

	@Override
	public void callServiceUpdate() {
		service.patch("fr", Long.valueOf(model.getIdentifier()), RestTool.writeDocument(model.getData()));
	}

}
