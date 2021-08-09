package fr.webmaker.common.response;

import java.util.List;

import fr.webmaker.common.Identifier;
import fr.webmaker.common.PagingData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionResponse<M, I extends Identifier<?>> {

	protected PagingData _paging;

	protected List<SingleResponse<M, I>> _embedded;

}
