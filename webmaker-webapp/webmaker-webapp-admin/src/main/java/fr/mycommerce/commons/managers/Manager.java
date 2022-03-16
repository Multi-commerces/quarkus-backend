package fr.mycommerce.commons.managers;

import java.io.Serializable;
import java.util.List;

import fr.mycommerce.commons.models.Model;
import fr.webmaker.data.BaseResource;

public interface Manager <M extends BaseResource> extends Serializable {
	
	Model<M> getModel();
	
	List<Model<M>> findAll();
	
	void create();
	
	void update();
	
	void delete(String id);

}
