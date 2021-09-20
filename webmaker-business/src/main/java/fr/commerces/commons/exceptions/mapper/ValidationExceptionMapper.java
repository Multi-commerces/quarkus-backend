package fr.commerces.commons.exceptions.mapper;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException>{

  @Override
  public Response toResponse(ValidationException exception) {
    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.TEXT_PLAIN)
                   .entity(exception.getMessage())
                   .build();
  }

}