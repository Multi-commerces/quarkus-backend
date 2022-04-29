package fr.mycommerce.view.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.mycommerce.commons.tools.RestTool;
import fr.mycommerce.service.product.ProductSheetRestClient;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.product.ProductSheetData;

/**
 * Backing Bean pour l'administration des données de base de la fiche produit
 * 
 * @author Julien ILARI
 *
 */
@ViewScoped
@Named("adminProductBasicMB")
public class ProductBasicMB extends AbstractProductMB<ProductSheetData> {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	private ProductSheetRestClient service;

	public ProductBasicMB() {
		super(FlowPage.BASIC);
	}

	@Override
	public byte[] callServiceFindById(String identifier) {
		return service.searchById(identifier);
	}

	@Override
	public void callServiceUpdate() {
		service.update(model.getIdentifier(), RestTool.writeDocument(model.getData()));
	}

}
