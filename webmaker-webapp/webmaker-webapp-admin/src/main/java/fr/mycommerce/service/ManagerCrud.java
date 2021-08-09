package fr.mycommerce.service;

import java.io.Serializable;

import fr.webmaker.common.Identifier;

/**
 * </h1>Crud Manager (create, read, update et delete)</h1>
 * <p>
 * on retrouve ici les appels à une ressource, que nous retrouvons de manière
 * (presque) systématique
 * </p>
 * 
 * @author Julien ILARI
 *
 * @param <Data> Réprésentation des données (DTO) renvoyées par la resource api
 * @param <I>    Idenfiant de la représentation
 */
public interface ManagerCrud<Data extends Serializable, I extends Identifier<Object>> extends Manager<Data, I> {

	ICrudResource<Data, I> getService();


	/**
	 * Requête de type post (création)
	 */
	default void create() {
		getService().post(getModel().getData());
	}

	/**
	 * Requête de type put (modification)
	 */
	default void update() {
		getService().put(getModel().getData());
	}

	/**
	 * Requête de type delete (suppression)
	 */
	default void delete(I id) {
		getService().delete(id);
	}

}
