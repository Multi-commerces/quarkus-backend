package fr.mycommerce.transverse;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.SingleResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe d'administration générique
 * 
 * @author Julien ILARI
 *
 */
@Slf4j
public abstract class AbstractView<Data extends Serializable, I extends Identifier<?>> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	protected ActionType action;

	@Getter
	@Setter
	protected Model<Data, I> model;

	/**
	 * Constructeur
	 * <p>
	 * Initilisation du 'model' et définition de l'action sur ActionType.DEFAULT
	 * </p>
	 */
	public AbstractView() {
		model = new Model<Data, I>();
		this.action = ActionType.DEFAULT;
	}

	/**
	 * Opération de Chargement (initialisation)
	 * 
	 **/
	public void onLoad() {
		final String id = JavaFacesTool.getValueParam("id");
		if (id != null) {
			SingleResponse<Data, I> response = callServiceFindById(id);
			model.setIdentifier(response.getIdentifier());
			model.setData(response.getData());
			action = ActionType.UPDATE;
		} else {
			action = ActionType.CREATE;
		}
	}

	public abstract I newIdentifier();

	/**
	 * Méthode de reset du 'model'
	 * 
	 **/
	public void reset(ActionEvent event) {
		if (action == ActionType.CREATE) {
			model.setIdentifier(newIdentifier());
		}
		model.setData(null);
	}

	/**
	 * Action sauvegarde item
	 * 
	 */
	public void saveAction(ActionEvent event) {
		try {
			switch (action) {
			case CREATE:
				callServiceCreate();
				break;
			case UPDATE:
				callServiceUpdate();
				break;
			default:
				log.warn("action({}) doit etre CREATE ou UPDATE !", action);
				return;
			}

			JavaFacesTool.sendFacesMessage("success of the action " + getAction().name(), 
					FacesMessage.SEVERITY_INFO,
					true);

			log.debug("Succès action {} !!!", action);

			// postSaveAction();
		} catch (Exception e) {
			/**
			 * Passage de la validation en échec avec création d'un facesMessage. Demande de
			 * passer à la phase Response, en ignorant les phases non encore exécutées.
			 */
			JavaFacesTool.sendFacesMessage("success of the action " + getAction().name(), 
					FacesMessage.SEVERITY_ERROR,
					true);
		}

	}

	/**
	 * Opération de création
	 */
	protected abstract void callServiceCreate();

	/**
	 * Opération de mise à jour
	 */
	protected abstract void callServiceUpdate();

	/**
	 * Opération de suppression par l'identifiant de item
	 * 
	 * @param id
	 */
	protected abstract void callServiceDelete(Long id);

	/**
	 * Opération de recherche par l'identifiant de l'item
	 * 
	 * @return
	 */
	public abstract SingleResponse<Data, I> callServiceFindById(String identifier);

	protected void handleNavigation(String outcome, boolean facesRedirect, String identifier) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();

		StringBuilder value = new StringBuilder(outcome);
		value.append("?faces-redirect=").append(facesRedirect);
		if (identifier != null) {
			value.append("&id=").append(identifier);

		}

		myNav.handleNavigation(facesContext, null, value.toString());
	}

	protected Long extractId() {
		String id = JavaFacesTool.getValueParam("id");
		if (id != null) {
			return Long.valueOf(id);
		}
		return null;
	}

	

}
