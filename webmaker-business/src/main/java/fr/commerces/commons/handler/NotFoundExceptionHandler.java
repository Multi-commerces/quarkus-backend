package fr.commerces.commons.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.commerces.commons.exceptions.crud.NotFoundException;
import fr.webmaker.commons.response.ErrorResponse;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse("404", exception.getMessage(), "identifier_not_found", "nolink",
				null);
		return Response.status(Status.NOT_FOUND).entity(errorResponse).build();
	}

}