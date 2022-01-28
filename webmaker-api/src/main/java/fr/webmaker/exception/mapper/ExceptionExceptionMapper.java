package fr.webmaker.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class ExceptionExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse("500",
				exception.getMessage() + (exception.getStackTrace() != null && exception.getStackTrace().length > 0
						? " " + exception.getStackTrace()[0].toString()
						: ""),
				"error_technical", "nolink", null);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	}
}