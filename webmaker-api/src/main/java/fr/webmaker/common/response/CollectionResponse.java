package fr.webmaker.common.response;

import java.util.List;

import fr.webmaker.common.Identifier;
import fr.webmaker.common.PagingData;
import lombok.Getter;
import lombok.Setter;

/**
 * Permet d'inclure la pagination dans a réponse
 * @author julien ILARI
 *
 * @param <M>
 * @param <I>
 */
@Getter
@Setter
public class CollectionResponse<M, I extends Identifier<?>> {

	protected PagingData _paging;

	protected List<SingleResponse<M, I>> _embedded;

}
