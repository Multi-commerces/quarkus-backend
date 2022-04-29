package fr.webmaker.data;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
public class BaseResource implements HasId<String> {

	public static final String RELATIONS = "relationships";
	
	@Id
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Schema(description = "Identifiant unique de la ressource", implementation = String.class, hidden = true)
	@JsonIgnore
	@EqualsAndHashCode.Include
	@ToString.Include
	protected String id;
	
	@Links
	@JsonInclude(Include.NON_NULL)
	@Schema(hidden = true)
	@JsonProperty(access = Access.READ_ONLY)
	protected com.github.jasminb.jsonapi.Links links;

	public BaseResource(String id) {
		super();
		this.id = id;
	}
	
	public void setLinks(com.github.jasminb.jsonapi.Links links) {
		this.links = links;
	}
}
