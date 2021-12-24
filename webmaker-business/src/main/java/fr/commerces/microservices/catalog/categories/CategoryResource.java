package fr.commerces.microservices.catalog.categories;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jasminb.jsonapi.DeserializationFeature;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.Links;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.categories.data.CategoryLangBaseData;
import fr.commerces.microservices.catalog.categories.data.CategoryLangRelationData;
import fr.commerces.microservices.catalog.categories.data.CategoryRelationData;
import fr.commerces.microservices.catalog.categories.data.ShemaCategoryData;
import fr.commerces.microservices.catalog.categories.data.ShemaCreateCategoryData;

@Path(CategoryResource.PATH)
@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
public class CategoryResource extends GenericResource {

	public static final String PATH = "/categories";

	final Map<String, String> meta = new HashMap<>();
	final Map<String, Link> linkMap = new HashMap<String, Link>();
	final Links links = new Links(linkMap);

	@Inject
	CategoryManager manager;

	@PostConstruct
	public void categoryResource() {

		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		converter = new ResourceConverter(objectMapper, CategoryLangRelationData.class, CategoryRelationData.class,
				CategoryLangBaseData.class);

		converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès.", content = @Content(mediaType = "application/vnd.api+json", schema = @Schema(title = "category", type = SchemaType.OBJECT, implementation = ShemaCategoryData.class))) })
	@Operation(operationId = "getCategories", summary = "Rechercher des catégories", description = "<h2>Opération de recherche des catégories</h2>"
			+ "<ul><b>Relations (relationships) :</b><ul>." + "<li>sous-catégories =>"
			+ CategoryRelationData.RELATION_SUB_CATEGORIES + "<li>traductions =>" + CategoryRelationData.RELATION_LANG)
	@GET
	@Path("/")
	public byte[] getCategories() throws Exception {
		var data = manager.findCategoryHierarchy();

		return converter.writeDocumentCollection(
				new JSONAPIDocument<List<CategoryRelationData>>(data, links, null, objectMapper));
	}

	@Operation(operationId = "getCategoryProducts", summary = "Recherche les produits de la catégorie", description = "Retourne les informations du produit.")
	@GET
	@Path("/{id}")
	public byte[] getCategory(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("id") @NotNull Long categoryId)
			throws DocumentSerializationException {

		final CategoryRelationData value = manager.findCategoryHierarchyById(categoryId);

		return converter.writeDocument(new JSONAPIDocument<CategoryRelationData>(value, objectMapper));
	}

	@GET
	@Path("/{categoryId}/langs")
	public byte[] getCategoryLangs(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") @NotNull Long categoryId)
			throws JsonProcessingException, IllegalAccessException, DocumentSerializationException {
		final List<CategoryLangRelationData> value = manager.findCategoryLangCompositeDataByCategoryId(categoryId);

		return converter
				.writeDocumentCollection(new JSONAPIDocument<List<CategoryLangRelationData>>(value, objectMapper));
	}

	@Operation(operationId = "getCategoryRelationshipsLang", summary = "Recherche les produits de la catégorie", description = "Retourne les informations du produit.")
	@GET
	@Path("/{id}/relationships/langs")
	public byte[] getCategoryRelationshipsLang(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("id") @NotNull @NotNull Long categoryId)
			throws DocumentSerializationException {
		final List<CategoryLangBaseData> relationships = manager.findRelationshipsLangs(categoryId);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_ID);
//		converter.disableSerializationOption(SerializationFeature.INCLUDE_ID);
		converter.disableSerializationOption(SerializationFeature.INCLUDE_META);
		converter.disableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);

		return converter
				.writeDocumentCollection(new JSONAPIDocument<List<CategoryLangBaseData>>(relationships, objectMapper));
	}

	@Operation(operationId = "getCategoryLang", 
			summary = "Recherche les produits de la catégorie", 
			description = "Retourne les informations du produit.")
	@Path("/{id}/langs/{languageCode}")
	@GET
	public byte[] getCategoryLang(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("id") 
			@NotNull Long categoryId,
			@Parameter(description = "Langue de la catégorie recherchée") 
			@PathParam("languageCode") 
			@NotNull LanguageCode languageCode)
			throws DocumentSerializationException {
		final CategoryLangRelationData data = manager.findCategoryLangCompositeData(categoryId, languageCode);

		return converter.writeDocument(new JSONAPIDocument<CategoryLangRelationData>(data, objectMapper));
	}

	@Operation(operationId = "categoryPost", 
			summary = "Créer une catégorie", 
			description = "Opération de création d'une catégorie.")
	@POST
	@Path("/")
	public Response postCategory(
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaCreateCategoryData.class)) }) byte[] flux)
			throws DocumentSerializationException {
		converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_TYPE_IN_RELATIONSHIP);

		var document = converter.readDocument(flux, CategoryData.class);
		final CategoryData data = document.get();

		var newId = manager.createCategory(null, data);

		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(newId)).build();

		return Response.created(uri).entity(getCategory(newId)).build();
	}

	@Path("/{id}")
	@PATCH
	public Response patch(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("id") 
			@NotNull final Long categoryId,
			final byte[] data) {

		var document = converter.readDocument(data, CategoryRelationData.class);
		final CategoryRelationData categoryHierarchyData = document.get();

		// Remplacement complet des sous-catégories non authorisé !
		if (categoryHierarchyData.getSubCategories() != null) {
			throw new ForbiddenException(CategoryRelationData.RELATION_SUB_CATEGORIES
					+ "Tentative de remplacement complet d'une relation à plusieurs non authorisé");
		}

		// Mise à jour (partiel de la catégory)
		if (categoryHierarchyData.getCategoryLangs() != null) {
			LanguageCode lang = LanguageCode.getByCode("fr", false);
			manager.update(categoryId, lang, document.get().getCategoryLangs().get(0));
		}

		if (categoryHierarchyData != null) {
			manager.update(categoryId, categoryHierarchyData);
		}

		return Response.noContent().build();
	}

}