package fr.mycommerce.commons.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.validation.ValidationException;

import org.primefaces.shaded.commons.io.IOUtils;

import fr.mycommerce.commons.perf.RenderMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JavaFacesTool implements Serializable {
	
	public static final long serialVersionUID = 1;
	public static final String REDIRECT_PART = "faces-redirect";
	public static final String RETURN_POINT_PART = "rp";
	public static final String UID_PART = "uid";


	public String getContextRoot() {
		return "/" + getCurrentContext().getExternalContext().getContextName();
	}

	public ResourceBundle getResourceBundle() {
		return getCurrentContext().getApplication().getResourceBundle(getCurrentContext(), "msg");
	}

	public String getResourceBundleMessage(final String key) {
		final ResourceBundle bundle = getResourceBundle();
		if (bundle.containsKey(key)) {
			return bundle.getString(key);
		}
		return key;
	}

	public FacesContext getCurrentContext() {
		return FacesContext.getCurrentInstance();
	}

	public Locale getLocale() {
		Locale locale = null;
		try {
			locale = getCurrentContext().getViewRoot().getLocale();
		} catch (Exception e) {
			try {
				locale = getCurrentContext().getExternalContext().getRequestLocale();
			} catch (Exception e2) {
				locale = new Locale("fr", "FR");
			}
		}
		return locale;
	}

	/*
	 * ----- méthodes
	 */

	/**
	 * Navigation par programmation
	 * 
	 * @param url
	 * @param redirect
	 * @param returnPointURL
	 * @param uid
	 */
	public void navigateTo(String url, boolean redirect, String returnPointURL, Integer uid) {
		ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) getCurrentContext()
				.getApplication().getNavigationHandler();
		navigationHandler.performNavigation("");
	}

	/**
	 * Envoi d'un message
	 * 
	 * @param text            : Texte ou identifiant de message dans le fichier
	 *                        ApplicationResources.properties
	 * @param severity        : Gravité du message
	 * @param useFlashContext : Utilisation du contexte flash (dans le cadre d'une
	 *                        redirection)
	 */
	public void sendFacesMessage(String text, Severity severity, boolean useFlashContext) {
		FacesContext facesContext = getCurrentContext();

		facesContext.getExternalContext().getFlash().setKeepMessages(useFlashContext);

		text = getResourceBundleMessage(text);

		if (severity == null) {
			severity = FacesMessage.SEVERITY_INFO;
		}

		FacesMessage facesMessage = new FacesMessage(severity, null, text);

		facesContext.addMessage(null, facesMessage);
	}
	
	protected static void addFlashMessage(final Severity severityName, final String message) {
		FacesMessage facesMessage = null;
		if (severityName.equals(FacesMessage.SEVERITY_INFO)) {
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", message);
		} else if (severityName.equals(FacesMessage.SEVERITY_ERROR)) {
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message);
		}else
		{
			return;
		}

		final FacesContext facesContext = FacesContext.getCurrentInstance();
	
		facesContext.addMessage(null, facesMessage);
	}

	/**
	 * Envoi d'un message lié à une Exception
	 * 
	 * @param e               : L'Exception à traiter
	 * @param useFlashContext : Utilisation du contexte flash (dans le cadre d'une
	 *                        redirection)
	 */
	public void sendFacesMessage(Exception e, boolean useFlashContext) {
		FacesContext facesContext = getCurrentContext();

		facesContext.getExternalContext().getFlash().setKeepMessages(useFlashContext);

		if (e instanceof ValidationException) {

			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "cause.toString()",
					" formatMessage(cause.getLibelle(), cause.getParameters())");
			facesContext.addMessage(null, facesMessage);

		} else {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.toString(), e.toString());
			facesContext.addMessage(null, facesMessage);
		}
	}

	/**
	 * Formatage d'un message avec d'éventuels paramètres
	 * 
	 * @param message    : Texte du message ou identifiant de message dans le
	 *                   fichier ApplicationResources.properties
	 * @param parameters : Tableau de paramètres ou null
	 * @return Le message formaté
	 */
	public String formatMessage(String message, Object[] parameters) {
		String formatted = message;
		int p1, p2 = 0;
		String value = "";

		try {
			if (getResourceBundle().containsKey(message)) {
				formatted = getResourceBundle().getString(message);
			}

			while ((p1 = formatted.indexOf("{")) != -1) {
				p2 = formatted.indexOf("}", p1);
				int idx = Integer.valueOf(formatted.substring(p1 + 1, p2));
				if (idx < parameters.length) {
					value = parameters[idx].toString();
				} else {
					value = "";
				}
				value = getResourceBundleMessage(value);
				formatted = formatted.substring(0, p1) + value + formatted.substring(p2 + 1);
			}
		} catch (Exception e) {
		}
		return formatted;
	}
	
	public String getValueParam(String param) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// la demande n'est pas une publication:
		if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
			// extraire l'id de la chaîne de requête
			Map<String, String> paramMap = facesContext.getExternalContext().getRequestParameterMap();
			return paramMap.get(param);
		}

		return null;
	}

	public Map<String, String> getValueByParam() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// la demande n'est pas une publication:
		if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
			// extraire l'id de la chaîne de requête
			Map<String, String> paramMap = facesContext.getExternalContext().getRequestParameterMap();
			return paramMap;
		}

		return new HashMap<String, String>();
	}
	
	/**
	 * Demande le mode de rendu (mobile ou ordinateur de bureau)
	 * @return
	 */
    private RenderMode getBrowserRenderMode() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
        return userAgent.toLowerCase().contains("mobile") ? RenderMode.MOBILE : RenderMode.WEB;
    }
    
    /**
     * Le mode de rendu (cf. getBrowserRenderMode)
     * @return
     */
    public String getRenderMode() {
        return getBrowserRenderMode().name();
    }
    
    /**
	 * Obtient le fichier sous forme d'octet
	 * 
	 * @exception IllegalArgumentException [error.arg.null] - argument file est null
	 * @param file élément reçu dans une demande POST multipart/form-data.
	 * @return le tableau d'octets demandé
	 */
	protected byte[] encodeToFile(final Part file) {
		if (file == null)
			return null;
		try (InputStream input = file.getInputStream()) {
			/*
			 * toByteArray met l'entrée en mémoire tampon en interne, il n'est donc pas
			 * nécessaire d'utiliser un BufferedInputStream.
			 */
			return IOUtils.toByteArray(input);
		} catch (IOException e) {
			throw new RuntimeException("error.techn.io.encode", e);
		}
	}
	
	/**
	 * Extraction de l'identifiant (param => id)
	 * @return
	 */
	protected Long extractId()
	{
		String id = getValueParam("id");
		if(id != null)
		{
			return Long.valueOf(id);
		}
		return null;
	}
}