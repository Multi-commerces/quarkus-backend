package fr.webmaker.commons.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Links;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class BaseResource {

	public static final String RELATIONS = "relationships";

	@Id
	@EqualsAndHashCode.Include
	@ToString.Include
	@JsonIgnore
	protected String id;

	@Links
	@JsonIgnoreProperties
	@JsonIgnore
	protected com.github.jasminb.jsonapi.Links links;

	public BaseResource(String id) {
		super();
		this.id = id;
	}

}
