package fr.commerces.microservices.catalog.categories.parent;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.Collections;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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

import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryLangData;
import fr.webmaker.exception.crud.NotFoundException;
import fr.webmaker.restfull.hateos.schema.IShemaData;

@Path("/categories")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Catégories - Parentes", description = "Ressource pour la gestion des catégories parentes")
public class CategoryParentResource extends JsonApiResource<CategoryData> {
	
	@Inject
	CategoryManager manager;
	
	interface ShemaCollectionData {
		IShemaData<CategoryData>[] getData();
	}
	
	public abstract class ShemaSingleData  {
		public abstract IShemaData<CategoryLangData> getData();
	}

	public CategoryParentResource() {
		super(CategoryData.class);
	}
	
	@Operation(operationId = "getCategoryRelationshipsParent", 
			summary = "Recherche les langues (les relations) de la catégorie", 
			description = "Retourne les relations (langues) de la catégories.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET, 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@GET
	@Path("/{categoryId}/relationships/parent")
	public Response getCategoryRelationshipsChildren(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") @NotNull @NotNull Long categoryId)
			throws DocumentSerializationException {
		return writeRelationships(Optional.ofNullable(manager.findCategoryHierarchyById(categoryId))
				.orElseThrow(() -> new NotFoundException(categoryId)).getSubCategories());
	}

	@Operation(operationId = "getCategoryParent", 
			summary = "Recherche les langues de la catégorie", 
			description = "Retourne les langues de la catégories.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET,
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@GET
	@Path("/{categoryId}/parent")
	public Response getCategoryChildren(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") @NotNull Long categoryId) {
		return writeResponse(
				Optional.ofNullable(manager.findCategoryHierarchyById(categoryId))
						.orElseThrow(() -> new NotFoundException(categoryId)).getSubCategories(),
				Collections.emptyList());
	}

	@Operation(operationId = "getCategoryParent", 
			summary = "Recherche la sous-catégorie de la catégorie", 
			description = "Retourne les informations du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET, 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@Path("/{categoryId}/parent/{parentId}")
	@GET
	public Response getCategoryParent(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") 
			@NotNull long categoryId,
			@Parameter(description = "Identifiant de la sous-catégorie") 
			@PathParam("childrenId") 
			@NotNull long childrenId)
			throws DocumentSerializationException {
		return writeResponse(
				Optional.ofNullable(manager.findCategoryHierarchyById(categoryId))
						.orElseThrow(() -> new NotFoundException(categoryId)).getSubCategories().stream().findAny()
						.orElseThrow(() -> new NotFoundException(childrenId)));
	}
	
	@Operation(operationId = "patchCategoryParent", 
		summary = "Modifier une catégorie", 
		description = "Opération de mise à jour d'une catégorie."
	)
	@APIResponses(value = { 
		@APIResponse(responseCode = "200", description = ConstApi.CODE20XPATCH),
		@APIResponse(responseCode = "204", description = ConstApi.CODE20XPATCH) 
	})
	@Path("/{categoryId}/parent")
	@PATCH
	public Response patchCategory(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") 
			final long categoryId,
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux) {
		var response = manager.replaceParent(categoryId, Optional.ofNullable(readData(flux)));
		return Response.ok(writeDocument(response)).build();
	}

}