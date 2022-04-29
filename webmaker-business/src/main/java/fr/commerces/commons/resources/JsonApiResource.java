package fr.commerces.commons.resources;

import java.lang.reflect.ParameterizedType;
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
import com.github.jasminb.jsonapi.RelationshipResolver;
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
import lombok.Setter;

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

	@Setter
	protected Class<? extends BaseResource> readDocumentClass;

	@Setter
	protected Class<? extends M> writeDocumentClass;

	private List<Class<?>> clazz;

	final protected Map<String, Object> meta = new HashMap<>();
	final protected Map<String, Link> linkMap = new HashMap<String, Link>();
	final protected Links links = new Links(linkMap);

	/**
	 * Toujours vrai pour le moment, on es chargera de le conditionner
	 */
	private boolean showLinks = false;

	class Teste implements RelationshipResolver {

		@Override
		public byte[] resolve(String relationshipURL) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void jsonApiResourcePostConstruct() {
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

		List<Class<?>> getClazz = getClazz();

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

		if (getClass().getSuperclass().getGenericSuperclass() instanceof ParameterizedType) {
			ParameterizedType superClass = ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass());
			clazz.add((Class<M>) superClass.getActualTypeArguments()[0]);
		} else if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
			ParameterizedType superClass = ((ParameterizedType) getClass().getGenericSuperclass());
			clazz.add((Class<M>) superClass.getActualTypeArguments()[0]);
		}

	}

	public JsonApiResource() {

	}

	public JsonApiResource(Class<? extends BaseResource> readDocumentClass) {
		this.readDocumentClass = readDocumentClass;
	}

	public JsonApiResource(Class<? extends BaseResource> readDocumentClass, Class<M> writeDocumentClass) {
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
				.map(o -> Long.valueOf(o.getId())).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	protected <T extends BaseResource> List<T> readCollection(byte[] data) {
		converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_INCLUSIONS);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_TYPE_IN_RELATIONSHIP);

		return converter.readDocumentCollection(data, readDocumentClass).get().stream().map(o -> (T) o)
				.collect(Collectors.toUnmodifiableList());
	}

	@SuppressWarnings("unchecked")
	protected <T extends BaseResource> T readData(byte[] data) {

		if (!clazz.isEmpty()) {

			String location = headers != null ? headers.getHeaderString("Location") : "";

			showLinks = headers != null && headers.getHeaderString("include-Links") != null
					&& headers.getHeaderString("include-Links").equals("true");

			converter = new ResourceConverter(objectMapper, location != null ? location : "",
					clazz.toArray(new Class<?>[clazz.size()]));
		} else {
			converter = new ResourceConverter(objectMapper);
		}

		converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
		// Désactiver : AUTORISER LES INCLUSIONS INCONNUES
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_INCLUSIONS);
		// Désactiver : AUTORISER LE TYPE INCONNU DANS LA RELATION
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_TYPE_IN_RELATIONSHIP);

		if (readDocumentClass == null) {
			java.lang.reflect.Type type = getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				this.readDocumentClass = (Class<M>) ((ParameterizedType) type).getActualTypeArguments()[0];
			}
		}

		// TODO : Controller
		// readDocument(flux).getResponseJSONNode().findValues("included");

		return (T) converter.readDocument(data, readDocumentClass).get();
	}

	protected <T> RootData<List<T>> writeRootData(List<T> data) {
		return new RootData<List<T>>(data);
	}

	protected <T> RootData<T> writeRootData(T data) {
		return new RootData<T>(data);
	}

	protected Response writeResponse(Object data, Collection<? extends IInclusion> include) {
		return Response.ok(writeDocument(data, include)).build();
	}

	/**
	 * Utilisation "relationships"
	 * 
	 * @param data
	 * @return
	 */
	protected Response writeRelationships(List<? extends BaseResource> data) {
		// DOCUMENT
		ObjectNode resultNode = objectMapper.createObjectNode();

		// DOCUMENT->DATA
		ArrayNode dataNode = resultNode.putArray("data");
		for (BaseResource resource : data) {
			String type = resource.getClass().getAnnotation(Type.class).value();
			String id = resource.getId();

			ObjectNode relationNode = objectMapper.createObjectNode();
			relationNode.put("type", type);
			relationNode.put("id", id);

			dataNode.add(relationNode);
		}

		// DOCUMENT->LINKS
		if (showLinks) {
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

	protected Response writeResponse(Object data) {
		return Response.ok(writeDocument(data)).build();
	}

	protected Response writeResponseCreated(Object data, String id) {
		return Response.created(uriInfo.getAbsolutePathBuilder().path(id).build()).entity(writeDocument(data)).build();
	}

	protected byte[] writeDocument(Object data, Collection<? extends IInclusion> include) {
		return writeDocument(data, include, true, false);
	}

	protected byte[] writeDocument(Object data) {
		return writeDocument(data, Collections.emptyList(), true, false);
	}

	protected byte[] writeDocument(Object data, Collection<? extends IInclusion> include, boolean includeMeta,
			boolean includeRelations) {

		if (!clazz.isEmpty()) {

			String location = headers != null ? headers.getHeaderString("Location") : "";

			showLinks = headers != null && headers.getHeaderString("include-Links") != null
					&& headers.getHeaderString("include-Links").equals("true");

			converter = new ResourceConverter(objectMapper, location != null ? location : "",
					clazz.toArray(new Class<?>[clazz.size()]));
		} else {
			converter = new ResourceConverter(objectMapper);
		}

		if (includeMeta) {
			// Il faudra permettre la désactivation des méta-données
			converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
		}
		if (includeRelations) {
			// Plus tard prendre en charge le paramètre include afin de pouvoir
			// personnaliser la réponse
			converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		} else {
			converter.disableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		}

		if (showLinks) {
			// Il faudra permettre la désactivation des liens
			converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		} else {
			converter.disableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		}

		if (isCollection(data)) {
			return collection((List<?>) data, include);
		}
		return single(data, include);
	}

	private byte[] single(Object data, Collection<? extends IInclusion> include) {
		try {
//			objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
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

			// Serialize included
			var builder = new SerializationSettings.Builder();
			builder.serializeLinks(showLinks);

			// Serialize included
			if (include != null && !include.isEmpty()) {
				converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
				List<String> relations = include.stream().map(IInclusion::getType).collect(Collectors.toList());

				builder.includeRelationship(String.join(",", relations));
			}

			if (data instanceof Optional) {
				return converter.writeDocument(new JSONAPIDocument<>(data, linkss, meta, objectMapper),
						builder.build());
			}

			return converter.writeDocument(new JSONAPIDocument<>(data, linkss, meta, objectMapper), builder.build());
		} catch (DocumentSerializationException e) {
			// TODO Ignore
		}
		return null;
	}

	private byte[] collection(List<?> data, Collection<? extends IInclusion> include) {
		try {
			linkMap.put("self", new Link(uriInfo.getPath()));

			Links linkss = new Links(linkMap);
			var builder = new SerializationSettings.Builder();
			builder.serializeLinks(showLinks);

			// Serialize included
			if (include != null && !include.isEmpty()) {
				converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
				List<String> relations = include.stream().map(IInclusion::getType).collect(Collectors.toList());

				builder.includeRelationship(String.join(",", relations));
			}

			return converter.writeDocumentCollection(new JSONAPIDocument<>(data, linkss, meta, objectMapper),
					builder.build());
		} catch (DocumentSerializationException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isCollection(Object ob) {
		return ob != null && isClassCollection(ob.getClass());
	}

	public static boolean isClassCollection(Class<?> c) {
		return Collection.class.isAssignableFrom(c) || Map.class.isAssignableFrom(c);
	}

}
