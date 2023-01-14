package fr.webmaker.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception>
{
	@Override
	public Response toResponse(Exception exception)
	{

		if (exception != null && exception.getCause() != null
			&& exception.getCause() instanceof PropertyBindingException)
		{
			PropertyBindingException ex = (PropertyBindingException) exception.getCause();
			ErrorResponse errorResponse = new ErrorResponse("400",
				ex.getPropertyName() + " n'est pas reconnu et les données ne peuvent pas être enregistrées",
				"validation_error", "nolink", null);

			return Response.status(Status.BAD_REQUEST).entity(errorResponse).build();
		}
		String cause = exception != null && exception.getCause() != null
			? exception.getCause().toString()
			: "error";
		ErrorResponse errorResponse = new ErrorResponse(
			"500",
			exception == null || exception.getMessage() == null
				? cause
				: exception.getMessage(),
			"request_invalid", "nolink",
			null);

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
	}
}