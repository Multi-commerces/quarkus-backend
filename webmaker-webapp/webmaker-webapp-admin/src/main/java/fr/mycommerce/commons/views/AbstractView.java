package fr.mycommerce.commons.views;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.openapitools.jackson.nullable.JsonNullableModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.tools.JavaFacesTool;
import fr.webmaker.data.BaseResource;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductPricingData;
import fr.webmaker.data.product.ProductSeoData;
import fr.webmaker.data.product.ProductShippingData;
import fr.webmaker.data.product.ProductStockData;
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
	 * Pour la lecture et écriture des réponse WEB-API
	 */
	protected ResourceConverter converter;
	
	/**
	 * ObjectMapper utilisé par le converter
	 */
	protected ObjectMapper objectMapper;
	
	private Class<?>[] clazz = {ProductData.class, ProductCompositeData.class,
			ProductLangCompositeData.class, 
			ProductPricingData.class, 
			ProductStockData.class, 
			ProductShippingData.class,
			ProductSeoData.class};
	
	private Class<?> classForConverter;
	
	/**
	 * Les données au format Json
	 * @return
	 * @throws IOException
	 */
	public String getJson() throws IOException {	
		return objectMapper.readTree(writeDocument(model.getData())).toPrettyString().trim();
	}

	/**
	 * Constructeur
	 * <p>
	 * Initilisation du 'model' et définition de l'action sur ActionType.DEFAULT
	 * </p>
	 */
	public AbstractView() {
		model = new Model<M>();
		this.action = ActionType.DEFAULT;
		
		classForConverter = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		
		
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JsonNullableModule());
		converter = new ResourceConverter(objectMapper, clazz);
	}
	
	protected byte[] writeDocument(Object data) {
		try {
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			
			
			if (data == null) {
				ObjectNode resultNode = objectMapper.createObjectNode();
				resultNode.set("data", null);
				
				byte[] result = null;
				try {
					result = objectMapper.writeValueAsBytes(resultNode);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				return result;
			}
			
			converter.disableSerializationOption(SerializationFeature.INCLUDE_LINKS);
			byte[] flux = converter.writeDocument(new JSONAPIDocument<>(data, objectMapper));
			
			try {
				objectMapper.readTree(flux).toPrettyString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return flux;
		} catch (DocumentSerializationException e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Opération de Chargement (initialisation)
	 * 
	 **/
	@SuppressWarnings("unchecked")
	public void onLoad() {
		final String id = JavaFacesTool.getValueParam("id");
		if (id != null) {			
			byte[] flux = callServiceFindById(id);	
			// TODO : A vérifier !! Le flux ne sera jamais null !!
			if(flux == null) 
			{
				// Initilisation (data : null)
				try {
					model.setData((M) classForConverter.newInstance());
					model.setIdentifier(id);
				} catch (Exception e) {
					return;
				} 
			}
			else
			{
				M response = (M) converter.readDocument(flux, classForConverter).get();
				if(response == null)
				{
					// Initilisation (data : null)
					try {
						response = (M) classForConverter.newInstance();
						model.setData(response);
						model.setIdentifier(id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				model.setData(response);
			}		
			
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
					false);
		} catch (Exception e) {
			/**
			 * Passage de la validation en échec avec création d'un facesMessage. Demande de
			 * passer à la phase Response, en ignorant les phases non encore exécutées.
			 */
			JavaFacesTool.sendFacesMessage("faild of the action " + getAction().name() + e.getMessage(), 
					FacesMessage.SEVERITY_ERROR,
					false);
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

	protected void handleNavigation(String outcome, boolean facesRedirect, String identifier) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();

		StringBuilder value = new StringBuilder(outcome)
			.append("?faces-redirect=")
			.append(facesRedirect);
		if (identifier != null) {
			value
				.append("&id=")
				.append(identifier);
		}

		myNav.handleNavigation(facesContext, null, value.toString());
	}

}
