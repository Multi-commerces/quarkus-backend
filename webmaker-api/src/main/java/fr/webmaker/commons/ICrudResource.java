package fr.webmaker.commons;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.commons.response.SingleResponse;

/**
 * Interface APPLICATION_JSON CRUD (Create Read Update Delete)
 * 
 * @author Julien ILARI 2020-09
 *
 * @param <Data> type bean de transfert
 * @param <I> type identifiant du bean
 */
@Produces(MediaType.APPLICATION_JSON)
public interface ICrudResource<Data, I extends Identifier<Object>> {

	/**
	 * Demander la liste des éléments
	 * 
	 * @return
	 */
	@GET
	@ClientHeaderParam(name = HttpHeaders.CONTENT_LANGUAGE, value = "{getLanguage}")
	public CollectionResponse<Data, I> get();
	
	@GET @Path("{id}")
	@ClientHeaderParam(name = HttpHeaders.CONTENT_LANGUAGE, value = "{getLanguage}")
	public SingleResponse<Data, I> getById(@PathParam("id") I identifiant);

	/**
	 * Demander l'ajout d'un nouveau élément
	 * 
	 * @param value élément à ajouter
	 * @return élément créé
	 */
	@POST
	@ClientHeaderParam(name = HttpHeaders.CONTENT_LANGUAGE, value = "{getLanguage}")
	public void post(Data value);

	/**
	 * Mise à jour d'un élément existant
	 * 
	 * @param value élément à mettre à jour
	 * @return
	 */
	@PUT
	public void put(Data value);

	/**
	 * Suppression d'un élément existant
	 * 
	 * @param identifiant de l'élément à supprimer.
	 */
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") I identifiant);


}