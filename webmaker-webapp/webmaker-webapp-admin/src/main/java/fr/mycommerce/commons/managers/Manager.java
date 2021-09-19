package fr.mycommerce.commons.managers;

import java.io.Serializable;
import java.util.List;

import fr.mycommerce.commons.models.Model;
import fr.webmaker.commons.identifier.Identifier;

public interface Manager <Data extends Serializable, I extends Identifier<?>> extends Serializable {
	
	Model<Data, I> getModel();
	
	List<Model<Data, I>> findAll();
	
	void create();
	
	void update();
	
	void delete(I id);

}
