package fr.mycommerce.commons.views;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.tools.JavaFacesTool;
import fr.webmaker.data.BaseResource;
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
public abstract class AbstractView<M extends BaseResource> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	protected ActionType action;

	@Getter
	@Setter
	protected Model<M> model;

	/**
	 * Constructeur
	 * <p>
	 * Initilisation du 'model' et définition de l'action sur ActionType.DEFAULT
	 * </p>
	 */
	public AbstractView() {
		model = new Model<M>();
		this.action = ActionType.DEFAULT;
	}

	/**
	 * Opération de Chargement (initialisation)
	 * 
	 **/
	public void onLoad() {
		final String id = JavaFacesTool.getValueParam("id");
		if (id != null) {
			byte[] flux = callServiceFindById(id);
			M response = null;// TODO convert JSON::API
			model.setData(response);
			
			action = ActionType.UPDATE;
		} else {
			action = ActionType.CREATE;
		}
	}

	/**
	 * Méthode de reset du 'model'
	 * 
	 **/
	public void reset(ActionEvent event) {
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
	public abstract byte[] callServiceFindById(String identifier);

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
