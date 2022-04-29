package fr.mycommerce.commons.managers;

import java.io.Serializable;
import java.util.List;

import fr.mycommerce.commons.models.Model;
import fr.webmaker.data.BaseResource;

public interface ICrudView <M extends BaseResource> extends Serializable {
	
	Model<M> getModel();
	
	/**
	 * Opération de récupération d'un item
	 * @return
	 */
	List<Model<M>> findAll();
	
	/**
	 * Opération de création d'un nouveau item
	 */
	void create();
	
	/**
	 * Opération de mise à jour d'un item existant
	 */
	void update();
	
	/**
	 * Opération de suppression d'un item existant
	 * @param id identifiant de l'item existant
	 */
	void delete(String id);

}
