package fr.mycommerce.view.products;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;

import fr.mycommerce.commons.tools.JavaFacesTool;
import fr.mycommerce.commons.views.AbstractView;
import fr.mycommerce.view.products.ProductFlowPage.FlowPage;
import fr.webmaker.data.BaseResource;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductSeoData;
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
	
	public AbstractProductMB(FlowPage flowPage) {
		super();
		this.activeIndexTabMenu = flowPage.getTabNUm();
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
		if (model != null) {
			String identifier = "";
			if (model.getData() instanceof ProductLangCompositeData) {
				identifier = ((ProductLangCompositeData) model.getData()).getId().toString();
			} else if (model.getData() instanceof ProductSeoData) {
				identifier = StringUtils.split(((ProductSeoData) model.getData()).getId(), "/")[0];
			} else {
				identifier = model.getIdentifier();
			}

			JavaFacesTool.handleNavigation(flowProductPage.getPage(), true, identifier);
		}
	}

	@Override
	protected void callServiceCreate() {
		// Ignore
	}

	@Override
	protected void callServiceDelete(Long id) {
		// Ignore
	}

}
