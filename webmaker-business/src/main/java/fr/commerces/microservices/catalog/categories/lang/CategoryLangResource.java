package fr.commerces.microservices.catalog.categories.lang;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.category.CategoryLangCompositeData;
import fr.webmaker.data.category.CategoryLangData;
import fr.webmaker.restfull.hateos.schema.IShemaData;

@Path("/categories")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Catégories - Langs", description = "Ressource pour la gestion des catégories")
public class CategoryLangResource extends JsonApiResource<CategoryLangCompositeData> {
	
	@Inject
	CategoryLangManager manager;
	
	interface ShemaCollectionData {
		IShemaData<CategoryLangData>[] getData();
	}
	
	public abstract class ShemaSingleData  {
		public abstract IShemaData<CategoryLangData> getData();
	}

	public CategoryLangResource() {
		super(CategoryLangData.class);
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
	@Path("/{categoryId}/relationships/langs")
	public Response getCategoryRelationshipsLang(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") @NotNull @NotNull Long categoryId)
			throws DocumentSerializationException {
		
		final List<CategoryLangCompositeData> relationships = manager.findCompositeByCategoryId(categoryId);
		return writeRelationships(relationships);
	}

	@Operation(operationId = "getCategoryLangs", 
			summary = "Recherche les langues de la catégorie", 
			description = "Retourne les langues de la catégories.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET,
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@GET
	@Path("/{categoryId}/langs")
	public Response getCategoryLangs(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") @NotNull Long categoryId) {
		return writeResponse(manager.findCompositeByCategoryId(categoryId), Collections.emptyList());
	}

	@Operation(operationId = "getCategoryLang", 
			summary = "Recherche les produits de la catégorie", 
			description = "Retourne les informations du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = ConstApi.CODE200GET, 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaSingleData.class))) })
	@Path("/{categoryId}/langs/{languageCode}")
	@GET
	public Response getCategoryLang(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("categoryId") 
			@NotNull Long categoryId,
			@Parameter(description = "Langue de la catégorie recherchée") 
			@PathParam("languageCode") 
			@NotNull LanguageCode languageCode)
			throws DocumentSerializationException {
		return writeResponse(manager.findCompositeByCategoryIdAndLanguageCode(categoryId, languageCode));
	}

}