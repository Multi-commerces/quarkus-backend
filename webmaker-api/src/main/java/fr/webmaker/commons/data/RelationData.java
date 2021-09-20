package fr.webmaker.commons.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;

/**
 * <h1>Objet de relations</h1>
 * <p>
 * L'objet relations représentent des références de l' objet ressource dans
 * lequel il est défini vers d'autres objets ressource.
 * </p>
 * 
 * @author Julien ILARI
 *
 */
@Getter
public class RelationData {

	/**
	 * liaison de ressources
	 */
	@JsonProperty("identifiers")
	private List<Identifier<?>> data;

	/**
	 * exemple :
	 * <ul>
	 * <li>"self": "http://example.com/products/1/relationships/author",</li>
     * <li>"related": "http://example.com/products/1/author"</li>
     * </ul>
	 */
	@JsonProperty("links")
	protected Map<String, String> links;
	
	/**
	 * méta-objet qui contient des méta-informations non standard sur la relation.
	 */
	private Object meta;
	
	public <I extends Identifier<?>>RelationData(String selfPath, List<I> data) {
		super();
		
		this.data = data != null ? new ArrayList<>(data) : null;
		
		this.links = new HashMap<>();
		links.put("related", selfPath);	
	}
}
