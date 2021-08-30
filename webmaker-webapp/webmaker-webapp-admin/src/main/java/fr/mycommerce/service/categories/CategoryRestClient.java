package fr.mycommerce.service.categories;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;

@Dependent
@Path("/languages/{languageCode}/categories")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
public interface CategoryRestClient  {

	@GET
	@Path("/") 
	CollectionResponse<CategoryData, LangID> getCategories(
			@PathParam("languageCode") String lang,
			@QueryParam("includeSubCategories") Boolean includeSubCategories);
	
	


}