package fr.mycommerce.commons.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.mycommerce.service.ICrudResource;
import fr.webmaker.data.BaseResource;

/**
 * </h1>Crud Manager (create, read, update et delete)</h1>
 * <p>
 * on retrouve ici les appels à une ressource, que nous retrouvons de manière
 * (presque) systématique
 * </p>
 * 
 * @author Julien ILARI
 *
 * @param <M> Réprésentation des données (DTO) renvoyées par la resource api
 * @param <I>    Idenfiant de la représentation
 */
public interface ManagerCrud<M extends BaseResource> extends Manager<M> {

	
	
	ICrudResource getService();


	/**
	 * Requête de type post (création)
	 */
	default void create() {
		ResourceConverter converter = new ResourceConverter();
		byte[] flux;
		try {
			flux = converter.writeDocument(new JSONAPIDocument<>(getModel().getData()));
		} catch (DocumentSerializationException e) {
			flux = null;
		}
		getService().post(flux);
	}

	/**
	 * Requête de type put (modification)
	 */
	default void update() {
		ResourceConverter converter = new ResourceConverter();
		byte[] flux;
		try {
			flux = converter.writeDocument(new JSONAPIDocument<>(getModel().getData()));
		} catch (DocumentSerializationException e) {
			flux = null;
		}
		getService().put(flux);
	}

	/**
	 * Requête de type delete (suppression)
	 */
	default void delete(String type, String id) {
		ObjectMapper mapper = new ObjectMapper();
		
		ObjectNode resultNode = mapper.createObjectNode();
		resultNode.put("type", type);
		resultNode.put("id", id);
		
		byte[] result = null;
		try {
			result = mapper.writeValueAsBytes(resultNode);
		} catch (Exception e) {
			// Ignore
		}
		
		getService().delete(Long.valueOf(id) ,result);
	}

}
