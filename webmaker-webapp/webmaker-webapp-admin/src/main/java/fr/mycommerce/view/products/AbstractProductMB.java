package fr.mycommerce.view.products;

import javax.faces.event.ActionEvent;

import fr.mycommerce.commons.views.AbstractView;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.BaseResource;
import fr.webmaker.data.product.ProductLangCompositeData;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstraction pour l'administration d'un produit
 * @author Julien ILARI
 *
 * @param <Data>
 */
public abstract class AbstractProductMB<M extends BaseResource> extends AbstractView<M> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	protected int activeIndexTabMenu;
	
	@Getter
	private FlowPage flowPage;
	
	public AbstractProductMB(FlowPage flowPage)
	{
		super();
		this.flowPage = flowPage;
		activeIndexTabMenu = flowPage.getTabNUm();
	}

	/**
	 * Action : changement du menu actif
	 * 
	 * @param event
	 */
	public void tabMenuAction(ActionEvent event) {
		final String value = (String) event.getComponent().getAttributes().get("activeIndexTabMenu");

		// Détection de index du tab
		activeIndexTabMenu = value != null ? Integer.valueOf(value) : -1;
		final FlowPage flowProductPage = FlowPage.stream().parallel()
				.filter(o -> o.getTabNUm().equals(activeIndexTabMenu)).findAny().orElse(FlowPage.BASIC);

		// Nagigation vers la tab détectée
		if(model != null)
		{
			if(model.getData() instanceof ProductLangCompositeData)
			{
				handleNavigation(flowProductPage.getPage(), true, 
						((ProductLangCompositeData) model.getData()).getProductId().toString());
			}
			else{
				handleNavigation(flowProductPage.getPage(), true, model.getIdentifier());
			}
		}
	}

}
