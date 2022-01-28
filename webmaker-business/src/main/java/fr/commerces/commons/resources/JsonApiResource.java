package fr.commerces.commons.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jasminb.jsonapi.DeserializationFeature;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.Links;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.SerializationSettings;
import com.github.jasminb.jsonapi.annotations.Type;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.microservices.authentification.AuthenticationContextProvider;
import fr.webmaker.data.BaseResource;
import fr.webmaker.restfull.hateos.IInclusion;
import fr.webmaker.restfull.hateos.RootData;
import lombok.Getter;

@Produces(ConstApi.MEDIA_JSON_API)
@Consumes(ConstApi.MEDIA_JSON_API)
public abstract class JsonApiResource<M extends BaseResource> {

	@Inject
	protected AuthenticationContextProvider authentication;

	@Context
	protected HttpHeaders headers;

	@Context
	@Getter
	protected UriInfo uriInfo;

	@Inject
	protected ObjectMapper objectMapper;

	protected ResourceConverter converter;

	protected Class<M> readDocumentClass;
	protected Class<? extends M> writeDocumentClass;

	final protected Map<String, Object> meta = new HashMap<>();
	final protected Map<String, Link> linkMap = new HashMap<String, Link>();
	final protected Links links = new Links(linkMap);
	
	private boolean showLinks = true;

	@PostConstruct
	public void jsonApiResourcePostConstruct() {	
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		
		List<Class<?>> getClazz = getClazz();

		final List<Class<?>> clazz;
		if (getClazz != null && !getClazz.isEmpty()) {
			clazz = new ArrayList<Class<?>>(getClazz);
		} else {
			clazz = new ArrayList<Class<?>>();
		}
		if (readDocumentClass != null) {
			clazz.add(readDocumentClass);
		}

		if (writeDocumentClass != null) {
			clazz.add(writeDocumentClass);
		}
		
		if (!clazz.isEmpty()) {
			converter = new ResourceConverter(objectMapper, clazz.toArray(new Class<?>[clazz.size()]));
		}
	}

	public JsonApiResource() {

	}
	
	public JsonApiResource(Class<M> readDocumentClass) {
		this.readDocumentClass = readDocumentClass;
	}

	public JsonApiResource(Class<M> readDocumentClass, Class<? extends M> writeDocumentClass) {
		this.readDocumentClass = readDocumentClass;
		this.writeDocumentClass = writeDocumentClass;
	}

	/**
	 * permet de fournir au converteur de nouvelles classes
	 * 
	 * @return
	 */
	public List<Class<?>> getClazz() {
		return Collections.emptyList();
	}

	protected List<Long> readDocumentAndCollectIds(byte[] data) {
		converter.enableSerializationOption(SerializationFeature.INCLUDE_ID);
		converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_INCLUSIONS);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_TYPE_IN_RELATIONSHIP);
		
		return converter.readDocumentCollection(data, readDocumentClass).get().stream()
				.map(o -> Long.valueOf(o.getId()))
				.collect(Collectors.toList());
	}

	
	protected M readData(byte[] data) {
		return readDocument(data).get();
	}

	protected JSONAPIDocument<M> readDocument(byte[] data) {
		converter.enableSerializationOption(SerializationFeature.INCLUDE_ID);
		converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_INCLUSIONS);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_TYPE_IN_RELATIONSHIP);
		
		return converter.readDocument(data, readDocumentClass);
	}

	
	protected  <T> Response writeRootData(List<T> data) {
		return Response.ok(new RootData<List<T>>(data)).build();
	}
	
	protected  <T> Response writeRootData(T data) {
		return Response.ok(new RootData<T>(data)).build();
	}
	
	protected Response writeResponse(Object data, List<? extends IInclusion> relationships) {
		return Response.ok(writeDocument(data, relationships)).build();
	}
	
	protected Response writeRelationships(List<? extends BaseResource> data) {
		// DOCUMENT
		ObjectNode resultNode = objectMapper.createObjectNode();
	
		// DOCUMENT->DATA
		ArrayNode dataNode = resultNode.putArray("data");
		for (BaseResource resource : data) {
			String type = resource.getClass()
					.getAnnotation(Type.class).value();
			String id = resource.getId();
			
			ObjectNode relationNode = objectMapper.createObjectNode();
			relationNode.put("type", type);
			relationNode.put("id", id);
			
			dataNode.add(relationNode);		
		}
		
		// DOCUMENT->LINKS
		if(showLinks)
		{
			String self = uriInfo.getPath();
			
			linkMap.put("self", new Link(self));
			linkMap.put("related", new Link(self.replace("relationships/", "")));
			Links linkss = new Links(linkMap);
			
			resultNode.set("links", objectMapper.valueToTree(linkss));
		}
		
		// RESULTAT (OK)
		byte[] result = null;
		try {
			result = objectMapper.writeValueAsBytes(resultNode);
		} catch (Exception e) {
			// TODO: Ignore ? gérer à l'avenir l'exception d'écriture du resultat
		}
		
		return Response.ok(result).build();
	}
	
	protected Response writeJsonApiResponse(Object data) {
		return Response.ok(writeDocument(data)).build();
	}

	protected Response writeResponseCreated(Object data, String id) {
		return Response.created(uriInfo.getAbsolutePathBuilder().path(id).build()).entity(writeDocument(data)).build();
	}

	protected byte[] writeDocument(Object data, List<? extends IInclusion> relationships) {
		return writeDocument(data, relationships, true, true, true);
	}
	
	protected byte[] writeDocument(Object data) {
		return writeDocument(data, Collections.emptyList(), true, true, true);
	}

	protected byte[] writeDocument(Object data, List<? extends IInclusion> relationships, boolean includeMeta, boolean includeRelations, boolean includeLinks) {	        
		if (includeMeta) {
			// Il faudra permettre la désactivation des méta-données
			converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
		}
		if (includeRelations) {
			// Plus tard prendre en charge le paramètre include afin de pouvoir
			// personnaliser la réponse
			converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		}
		if (includeLinks) {
			// Il faudra permettre la désactivation des liens
			converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		}

		if (isCollection(data)) {
			return collection((List<?>) data, relationships);
		}
		return single(data);
	}
	
	

	private byte[] single(Object data) {
		try {
			objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
			linkMap.put("self", new Link(uriInfo.getPath()));
			Links linkss = new Links(linkMap);
			
			if (data == null || (data instanceof Optional && ((Optional<?>) data).isEmpty())) {
				ObjectNode resultNode = objectMapper.createObjectNode();
				resultNode.set("data", null);
				resultNode.set("links", objectMapper.valueToTree(linkss));
				
				byte[] result = null;
				try {
					result = objectMapper.writeValueAsBytes(resultNode);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				return result;
			}
			
			if (data instanceof Optional) {
				return converter.writeDocument(new JSONAPIDocument<>(data, linkss, meta, objectMapper));
			}

			return converter.writeDocument(new JSONAPIDocument<>(data, objectMapper));
		} catch (DocumentSerializationException e) {
			// TODO Ignore
		}
		return null;
	}
	
	

	private byte[] collection(List<?> data, List<? extends IInclusion> relationships) {
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		try {
			linkMap.put("self", new Link(uriInfo.getPath()));
			
			Links linkss = new Links(linkMap);
			var builder = new SerializationSettings.Builder();
			
			return converter.writeDocumentCollection(new JSONAPIDocument<>(data, linkss, meta, objectMapper), builder.build());
		} catch (DocumentSerializationException e) {

		}
		return null;
	}

	public static boolean isCollection(Object ob) {
		return ob != null && isClassCollection(ob.getClass());
	}

	public static boolean isClassCollection(Class<?> c) {
		return Collection.class.isAssignableFrom(c) || Map.class.isAssignableFrom(c);
	}

}
