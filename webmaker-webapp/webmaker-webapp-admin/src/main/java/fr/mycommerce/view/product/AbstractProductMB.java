package fr.mycommerce.view.product;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import fr.mycommerce.transverse.AbstractView;
import fr.mycommerce.view.product.ProductFlowPage.FlowPage;
import fr.webmaker.common.Identifier;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstraction pour l'administration d'un produit
 * @author Julien ILARI
 *
 * @param <Data>
 */
public abstract class AbstractProductMB<Data extends Serializable, I extends Identifier<?>> extends AbstractView<Data, I> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	protected int activeIndexTabMenu;
	
	abstract FlowPage getFlowPage();
	
	public AbstractProductMB()
	{
		activeIndexTabMenu = getFlowPage().getTabNUm();
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
		handleNavigation(flowProductPage.getPage(), true, String.valueOf(model.getIdentifier().getId()));
	}

}
