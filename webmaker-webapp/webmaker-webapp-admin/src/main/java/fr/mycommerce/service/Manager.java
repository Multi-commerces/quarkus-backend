package fr.mycommerce.service;

import java.io.Serializable;
import java.util.List;

import fr.mycommerce.transverse.Model;
import fr.webmaker.common.Identifier;

public interface Manager <Data extends Serializable, I extends Identifier<?>> extends Serializable {
	
	Model<Data, I> getModel();
	
	List<Model<Data, I>> findAll();
	
	void create();
	
	void update();
	
	void delete(I id);

}
