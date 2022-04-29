package fr.mycommerce.commons.views;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.tools.JavaFacesTool;
import fr.mycommerce.commons.tools.RestTool;
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

	private Class<M> classForConverter;

	/**
	 * Les données au format Json
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getJson() throws IOException {
		return RestTool.getJson(model.getData());
	}

	/**
	 * Constructeur
	 * <p>
	 * Initilisation du 'model' et définition de l'action sur ActionType.DEFAULT
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public AbstractView() {
		model = new Model<M>();
		this.action = ActionType.DEFAULT;

		classForConverter = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Opération de Chargement (initialisation)
	 * 
	 **/
	public void onLoad() {
		if (classForConverter == null) {
			throw new IllegalStateException("classForConverter est null");
		}

		final String id = JavaFacesTool.getValueParam("id");
		this.action = !StringUtils.isBlank(id) ? ActionType.UPDATE : ActionType.CREATE;

		final M response = action == ActionType.UPDATE 
				? RestTool.readData(callServiceFindById(id), classForConverter)
				: newInstance();

		if (response == null) {
			this.action = ActionType.DEFAULT;
			return;
		}

		
		model.setData(response);
		model.setIdentifier(id);
	}

	private M newInstance() {
		try {
			return (M) classForConverter.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Méthode de reset du 'model'
	 * 
	 **/
	public void reset(ActionEvent event) {
		model.setIdentifier(null);
		model.setData(newInstance());
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

			JavaFacesTool.sendFacesMessage("success of the action " + getAction().name(), FacesMessage.SEVERITY_INFO,
					false);
		} catch (Exception e) {
			/**
			 * Passage de la validation en échec avec création d'un facesMessage. Demande de
			 * passer à la phase Response, en ignorant les phases non encore exécutées.
			 */
			JavaFacesTool.sendFacesMessage("faild of the action " + getAction().name() + e.getMessage(),
					FacesMessage.SEVERITY_ERROR, false);
		}

	}

	/**
	 * Opération de création de l'item en-cours de rédaction
	 */
	protected abstract void callServiceCreate();

	/**
	 * Opération de mise à jour de l'item en-cours d'édition
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

}
