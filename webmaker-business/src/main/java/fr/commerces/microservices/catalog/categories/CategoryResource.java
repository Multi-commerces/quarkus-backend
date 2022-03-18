package fr.commerces.microservices.catalog.categories;

import static fr.commerces.commons.resources.ConstApi.CODE204DELETE;
import static fr.commerces.commons.resources.ConstApi.CODE404DELETE;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.lang.CategoryLangManager;
import fr.webmaker.data.category.CategoryCompositeData;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryLangCompositeData;
import fr.webmaker.data.category.CategoryLangData;
import fr.webmaker.restfull.hateos.IInclusion;
import fr.webmaker.restfull.hateos.schema.IShemaData;

@Path("/categories")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
public class CategoryResource extends JsonApiResource<CategoryData> {

	@Inject
	CategoryManager manager;
	
	@Inject
	CategoryLangManager langManager;
	
	interface ShemaCollectionData {
		IShemaData<CategoryData>[] getData();
	}
	
	public abstract class ShemaSingleData  {
		public abstract IShemaData<CategoryData> getData();
	}
	
	@Override
	public List<Class<?>> getClazz() {
		return List.of(CategoryData.class, CategoryCompositeData.class, CategoryLangData.class, CategoryLangCompositeData.class);
	}
	
	public enum CategorieRelation implements IInclusion {

		CATEGORIES("category");

		private String type;

		CategorieRelation(String type) {
			this.type = type;
		}

		@Override
		public String getType() {
			return type;
		}

	}

	@Operation(operationId = "getCategories", 
			summary = "Rechercher des catégories", 
			description = "Opération de recherche des catégories")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès.", 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(title = "category", type = SchemaType.OBJECT, implementation = ShemaCollectionData.class))) })
	@GET
	@Path("/")
	public Response getCategories(@Parameter(description = "Inclusion de ressources connexes")
	@QueryParam(value = "include")  List<CategorieRelation> include) throws Exception {
		return writeResponse(manager.findCategoryHierarchy(), include);

	}

	@Operation(operationId = "getCategory", 
			summary = "Recherche une catégorie", 
			description = "Opération de recherche d'une catégorie")
	@APIResponses(value = {
		@APIResponse(responseCode = "200", description = ConstApi.CODE200GET, 
				content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
				schema = @Schema(implementation = ShemaSingleData.class))) }
	)
	@GET
	@Path("/{categoryId}")
	public Response getCategory(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") long categoryId)
			throws DocumentSerializationException {
		// meta-data (childrenCount, articleCount)
		
		return writeJsonApiResponse(manager.findCategoryHierarchyById(categoryId));
	}

	@Operation(operationId = "postCategory", 
			summary = "Créer une catégorie", 
			description = "Opération de création d'une catégorie."
	)
	@APIResponses(value = { 
			@APIResponse(responseCode = "201", description = ConstApi.CODE201POST)
	})
	@POST
	@Path("/")
	public Response postCategory(
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux)
			throws DocumentSerializationException {
		converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
		converter.disableDeserializationOption(DeserializationFeature.ALLOW_UNKNOWN_TYPE_IN_RELATIONSHIP);

		var document = converter.readDocument(flux, CategoryData.class);
		final CategoryData data = document.get();

		var newId = manager.createCategory(null, data);

		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(newId)).build();

		return Response.created(uri).entity(getCategory(newId)).build();
	}

	@Operation(operationId = "patchCategory", 
		summary = "Modifier une catégorie", 
		description = "Opération de mise à jour d'une catégorie."
	)
	@APIResponses(value = { 
		@APIResponse(responseCode = "200", description = ConstApi.CODE20XPATCH),
		@APIResponse(responseCode = "204", description = ConstApi.CODE20XPATCH) 
	})
	@Path("/{categoryId}")
	@PATCH
	public Response patchCategory(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") 
			final long categoryId,
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux) {

		// Mise à jour (partiel de la catégory)
		manager.update(categoryId, readData(flux));

		return Response.noContent().build();
	}
	
	@Operation(operationId = "deleteCategory", 
		summary = "Supprimer une catégorie", 
		description = "Opération de suppression d'une catégorie."
	)
	@APIResponses(value = { 
		@APIResponse(responseCode = "204", description = CODE204DELETE),
		@APIResponse(responseCode = "404", description = CODE404DELETE) 
	})
	@Path("/{categoryId}")
	@DELETE
	public void deleteCategory(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") long categoryId) {
		manager.delete(categoryId);
	}

}