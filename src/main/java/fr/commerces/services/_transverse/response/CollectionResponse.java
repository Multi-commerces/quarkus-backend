package fr.commerces.services._transverse.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fr.commerces.services._transverse.data.PagingData;
import lombok.Getter;
import lombok.Setter;

@JsonRootName("test")
@Getter
@Setter
public class CollectionResponse<M, I> {
	
	@JsonProperty("_paging")
	protected PagingData paging;

	@JsonProperty("_embedded")
	protected List<SingleResponse<M, I>> embedded;



}
