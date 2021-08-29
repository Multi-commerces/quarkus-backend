package fr.webmaker.commons.response;

import java.util.List;

import fr.webmaker.commons.PagingData;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Data;

/**
 * Permet d'inclure la pagination dans a réponse
 * @author julien ILARI
 *
 * @param <M>
 * @param <I>
 */
@Data
public class CollectionResponse<M, I extends Identifier<?>> {

	protected PagingData paging;

	protected List<SingleResponse<M, I>> collection;

	public CollectionResponse() {
		super();
	}
	
	public CollectionResponse(final List<SingleResponse<M, I>> collection) {
		super();
		this.collection = collection;
	}
	
	

}
