package fr.commerces.services._transverse;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class RessourceCriteria<M> {

	private String order;
	
	private Integer page;
	
	private Integer limit;

}