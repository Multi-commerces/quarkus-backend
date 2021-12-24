package fr.webmaker.microservices.catalog.categories.restapi;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@Path("/languages/{languageCode}/categories")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
public interface CategoryRestClient  {

//	@GET
//	@Path("/") 
//	CollectionResponse<CategoryLangData, LangID> getCategories(
//			@PathParam("languageCode") String lang,
//			@QueryParam("includeSubCategories") Boolean includeSubCategories);
	
	


}