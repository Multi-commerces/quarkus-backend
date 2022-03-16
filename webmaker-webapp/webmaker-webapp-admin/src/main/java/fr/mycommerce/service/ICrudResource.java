package fr.mycommerce.service;

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

/**
 * Interface APPLICATION_JSON CRUD (Create Read Update Delete)
 * 
 * @author Julien ILARI 2020-09
 *
 * @param <Data> type bean de transfert
 * @param <I> type identifiant du bean
 */
@Produces(MediaType.APPLICATION_JSON)
public interface ICrudResource {

	/**
	 * Demander la liste des éléments
	 * 
	 * @return
	 */
	@GET
	@ClientHeaderParam(name = HttpHeaders.CONTENT_LANGUAGE, value = "{getLanguage}")
	public byte[] get();
	
	/**
	 * Demander un seul élément
	 * @param id
	 * @return
	 */
	@GET @Path("{id}")
	@ClientHeaderParam(name = HttpHeaders.CONTENT_LANGUAGE, value = "{getLanguage}")
	public byte[] getById(@PathParam("id") long id);

	/**
	 * Demander l'ajout d'un nouveau élément
	 * 
	 * @param value élément à ajouter
	 * @return élément créé
	 */
	@POST
	@ClientHeaderParam(name = HttpHeaders.CONTENT_LANGUAGE, value = "{getLanguage}")
	public void post(byte[] flux);

	/**
	 * Mise à jour d'un élément existant
	 * 
	 * @param value élément à mettre à jour
	 * @return
	 */
	@PUT
	public void put(byte[] flux);

	/**
	 * Suppression d'un élément existant
	 * 
	 * @param identifiant de l'élément à supprimer.
	 */
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long identifiant, byte[] flux);


}