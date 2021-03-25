package fr.commerces.services._transverse;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenericResponse<M, I> {

	@JsonProperty("id")
	protected I id;

	@JsonProperty("data")
	protected M data;

	@JsonProperty("_links")
	protected List<Link> links = new ArrayList<>();

	@JsonProperty("_self")
	protected Link self;
	

}
