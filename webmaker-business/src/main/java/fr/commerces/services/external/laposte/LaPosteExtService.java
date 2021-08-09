package fr.commerces.services.external.laposte;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;



/**
 * <h1>Rest Client "ControlAdresse" de "laposte.fr"</h1>
 * <p>
 * Pour plus de détail sur API controlAdresse de La Poste
 * {@link}https://developer.laposte.fr/products/controladresse/latest
 * </p>
 * 
 * @author julien ILARI
 *
 */
@Dependent
@Path("/controladresse/v1")
@RegisterRestClient(configKey = "laposte-api")
public interface LaPosteExtService extends AutoCloseable {

	@GET @Path("/adresses")
	@ClientHeaderParam(name = "X-Okapi-Key", value = "{getKeyApi}")
	public List<AddressResponse> find(@QueryParam("q") String address);

	/**
	 * Fourni la clé api pour l'utilisation des services de "laposte.fr"
	 * @return
	 */
	default String getKeyApi() {
		return "LwfnPPEz6+t3w3QWfKNNulVyHRplG1b3tYNOLPnCD6PSlTur9j/8ljZb6xAZ0nz+";
	}

}
