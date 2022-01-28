package fr.commerces.microservices.catalog.categories.basic;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.net.URI;
import java.util.Collections;
import java.util.List;

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

import com.github.jasminb.jsonapi.DeserializationFeature;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.data.ShemaCategoryData;
import fr.commerces.microservices.catalog.categories.data.ShemaCreateCategoryData;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangData;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangRelationData;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryRelationData;

@Path(CategoryResource.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
public class CategoryResource extends JsonApiResource<CategoryData> {

	public static final String PATH = "/categories";

	@Inject
	CategoryManager manager;

	public CategoryResource() {
		super(CategoryData.class, CategoryRelationData.class);
	}
	
	@Override
	public List<Class<?>> getClazz() {
		return List.of(CategoryLangData.class, CategoryLangRelationData.class);
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès.", 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(title = "category", type = SchemaType.OBJECT, implementation = ShemaCategoryData.class))) })
	@Operation(operationId = "getCategories", 
		summary = "Rechercher des catégories", 
		description = "Opération de recherche des catégories")
	@GET
	@Path("/")
	public Response getCategories() throws Exception {
		return writeResponse(manager.findCategoryHierarchy(), Collections.emptyList());

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
//		if (categoryHierarchyData.getCategory() != null) {
//			LanguageCode lang = LanguageCode.getByCode("fr", false);
//			manager.update(categoryId, lang, document.get().getCategories().get(0));
//		}

		if (categoryHierarchyData != null) {
			manager.update(categoryId, categoryHierarchyData);
		}

		return Response.noContent().build();
	}

}