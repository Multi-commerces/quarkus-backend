package fr.webmaker.commons.response;

import java.beans.Transient;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.PagingData;
import fr.webmaker.commons.data.LinkData.REL;
import fr.webmaker.commons.data.SimpleData;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;

/**
 * Composition générique de la reponse, comprenant une collection d'objets
 * 
 * @author julien ILARI
 *
 * @param <M> Data
 * @param <I> Identifier
 */
@Getter
public class CollectionResponse<M, I extends Identifier<?>> {
	
	public static final String JSON_NODE_PAGE = "meta";
	public static final String JSON_NODE_ITEMS = "data";
	public static final String JSON_NODE_NAV = "links";
	public static final String JSON_NODE_SELF = "self";
	public static final String JSON_NODE_INCLUDES = "included";


	/**
	 * Pagination Les clés suivantes DOIVENT être utilisées pour les liens de
	 */
	@JsonProperty(JSON_NODE_PAGE)
	protected PagingData page;
	
	/**
	 * Navigation
	 * <ul>
	 * <li>first: la première page de données</li>
	 * <li>last: la dernière page de données</li>
	 * <li>prev: la page précédente de données</li>
	 * <li>next: la page de données suivante</li>
	 * </ul>
	 * 
	 * <p>
	 * Les clés DOIVENT être omises ou avoir une valeur null pour indiquer qu'un lien
	 * particulier n'est pas disponible.
	 * </p>
	 */
	@JsonProperty(JSON_NODE_NAV)
	final Map<REL, String> nagigation;
	
	/**
	 * Liste des items
	 */
	@JsonProperty(JSON_NODE_ITEMS)
	protected List<SingleCompositeData<M, I>> collection;
	
	/**
	 * <h1>Ressources incluses</h1>
	 * https://jsonapi.org/format/#document-compound-documents
	 * 
	 * <p>
	 * Dans un document composé, toutes les ressources incluses DOIVENT être
	 * représentées sous la forme d'un tableau d' objets ressources dans un included
	 * </p>
	 */
	@JsonProperty(JSON_NODE_INCLUDES)
	protected Map<String, List<? extends SingleCompositeData<? extends SimpleData, ? extends Identifier<?>>>> included;

	
	private CollectionResponse(Builder<M, I> builder) {
		this.page = builder.page;
		this.collection = builder.collection;
		this.nagigation = builder.nagigation;
		this.included = builder.included;
	}

	public CollectionResponse() {
		super();
		collection = new ArrayList<>();

		nagigation = new HashMap<>();
		nagigation.put(REL.FIRST, null);
		nagigation.put(REL.NEXT, null);
		nagigation.put(REL.PREV, null);
		nagigation.put(REL.LAST, null);
		
		this.included = new HashMap<>();
	}

	public CollectionResponse(final Collection<SingleCompositeData<M, I>> collection) {
		this();
		this.collection.addAll(collection);
	}

	@Transient
	public void clear() {
		collection.clear();
	}

	@Transient
	public boolean containsKey(Object arg0) {
		return collection.stream().anyMatch(o -> o.getIdentifier().equals(arg0));
	}

	@Transient
	public boolean containsValue(Object arg0) {
		return collection.stream().anyMatch(o -> o.getData().equals(arg0));
	}

	@Transient
	public M get(Identifier<?> arg0) {
		return collection.stream().filter(o -> o.getIdentifier().equals(arg0)).map(o -> o.getData()).findAny()
				.orElse(null);
	}

	@Transient
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@Transient
	public Set<I> keySet() {
		return collection.stream().map(o -> o.getIdentifier()).collect(Collectors.toSet());
	}

	@Transient
	public M put(I arg0, M arg1) {
		collection.add(new SingleCompositeData<M, I>(arg0, arg1));
		return arg1;
	}

	@Transient
	public void putAll(Map<? extends I, ? extends M> arg0) {
		arg0.entrySet().stream().forEach(entry -> {
			final SingleCompositeData<M, I> singleResponse = new SingleCompositeData<M, I>();
			singleResponse.setIdentifier(entry.getKey());
			singleResponse.setData(entry.getValue());

			collection.add(singleResponse);
		});
	}

	@Transient
	public boolean remove(Object arg0) {
		return collection.remove(arg0);
	}

	@Transient
	public int size() {
		return collection.size();
	}

	@Transient
	public Collection<M> values() {
		return collection.stream().map(o -> o.getData()).collect(Collectors.toList());
	}

	/**
	 * Creates builder to build {@link CollectionResponse}.
	 * 
	 * @return created builder
	 */
	public static <M, I extends Identifier<?>> Builder<M, I> builder(UriInfo uriInfo) {
		return new Builder<M, I>(uriInfo);
	}

	/**
	 * Builder to build {@link CollectionResponse}.
	 */
	public static final class Builder<M, I extends Identifier<?>> {
		private PagingData page;
		private List<SingleCompositeData<M, I>> collection = Collections.emptyList();
		
		private Map<REL, String> nagigation = new HashMap<>();
		private Link self;
		
		private Map<String, List<? extends SingleCompositeData<? extends SimpleData, ? extends Identifier<?>>>> included;

		
		private Builder(UriInfo uriInfo) {
			this.self = Link.fromUriBuilder(uriInfo.getRequestUriBuilder())
	                .rel("self").build();
		}

		public Builder<M, I> paging(PagingData page) {
			this.page = page;
			URI uri =  self.getUri();
			
			long current = page.getNumber();
			long last = page.getTotalPages();
			long next = current < last ? current + 1 : last;
			long prev = current > 1 ? current - 1 : 1;
			
			nagigation = new HashMap<>();
			nagigation.put(REL.FIRST, uri.getPath() + "?page=1" + "&size=" + page.getSize());
			nagigation.put(REL.NEXT, uri.getPath() + "?page=" + next + "&size=" + page.getSize());
			nagigation.put(REL.PREV, uri.getPath() + "?page=" + prev + "&size=" + page.getSize());
			nagigation.put(REL.LAST, uri.getPath() + "?page=" + page.getTotalPages() + "&size=" + page.getSize());			
			
			return this;
		}

		public Builder<M, I> collection(List<SingleCompositeData<M, I>> collection) {
			this.collection = collection;
			return this;
		}
		
		public Builder<M, I> included(String key, List<? extends SingleCompositeData<? extends SimpleData, ? extends Identifier<?>>> included) {
			if(this.included == null)
			{
				this.included =new HashMap<String, List<? extends SingleCompositeData<? extends SimpleData, ? extends Identifier<?>>>>();
			}
			
			this.included.put(key, included);
			
			
			return this;
		}

		public CollectionResponse<M, I> build() {
			return new CollectionResponse<M, I>(this);
		}
	}

}
