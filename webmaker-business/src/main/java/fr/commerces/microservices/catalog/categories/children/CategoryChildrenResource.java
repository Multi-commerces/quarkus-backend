package fr.commerces.microservices.catalog.categories.children;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.webmaker.data.category.CategoryCompositeData;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.exception.crud.NotFoundException;
import fr.webmaker.restfull.hateos.schema.IShemaData;

@Path("/categories")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Catégories - Children", description = "Ressource pour la gestion des sous-catégories")
public class CategoryChildrenResource extends JsonApiResource<CategoryData> {
	
	@Inject
	CategoryManager manager;
	
	interface ShemaCollectionData {
		IShemaData<CategoryData>[] getData();
	}
	
	public abstract class ShemaSingleData  {
		public abstract IShemaData<CategoryData> getData();
	}

	public CategoryChildrenResource() {
		super(CategoryData.class, CategoryCompositeData.class);
	}
	
	@Operation(operationId = "getCategoryRelationshipsLang", 
			summary = "Recherche les langues (les relations) de la catégorie", 
			description = "Retourne les relations (langues) de la catégories.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET, 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@GET
	@Path("/{categoryId}/relationships/children")
	public Response getCategoryRelationshipsChildren(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") final long categoryId) {
		return writeRelationships(Optional.ofNullable(manager.findCategoryHierarchyById(categoryId))
				.orElseThrow(() -> new NotFoundException(categoryId)).getSubCategories());
	}

	@Operation(operationId = "getCategoryChildren	", 
			summary = "Recherche les langues de la catégorie", 
			description = "Retourne les langues de la catégories.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET,
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@GET
	@Path("/{categoryId}/children")
	public Response getCategoryChildren(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") final long categoryId) {
		return writeJsonApiResponse(
				manager.findCategoryHierarchyById(categoryId).getSubCategories());
	}

	@Operation(operationId = "getCategoryChildren", 
			summary = "Recherche la sous-catégorie de la catégorie", 
			description = "Retourne les informations du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET, 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@Path("/{categoryId}/children/{childrenId}")
	@GET
	public Response getCategoryChildren(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") final long categoryId,
			@Parameter(description = "Identifiant de la sous-catégorie") 
			@PathParam("childrenId") final long childrenId) {
		return writeJsonApiResponse(
				manager.findCategoryHierarchyById(categoryId).getSubCategories().stream()
						.filter(o -> Long.valueOf(o.getId()).equals(childrenId)).findAny()
						.orElseThrow(() -> new NotFoundException(childrenId)));
	}
	
	@Operation(operationId = "patchCategoryChilden", 
		summary = "Modifier une catégorie", 
		description = "Opération de mise à jour d'une catégorie."
	)
	@APIResponses(value = { 
		@APIResponse(responseCode = "200", description = ConstApi.CODE20XPATCH),
		@APIResponse(responseCode = "204", description = ConstApi.CODE20XPATCH) 
	})
	@Path("/{categoryId}/children")
	@PATCH
	public Response patchCategory(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") 
			final long categoryId,
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux) {
		var response = manager.replaceChilden(categoryId, readCollection(flux));
		return Response.ok(writeDocument(response)).build();
	}

}