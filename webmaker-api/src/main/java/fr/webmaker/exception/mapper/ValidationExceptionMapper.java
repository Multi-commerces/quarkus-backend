package fr.webmaker.exception.mapper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException>{

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    
	  
	  ErrorResponse errorResponse = 
			  new ErrorResponse(String.valueOf(Status.BAD_REQUEST.getStatusCode()), 
					  "Une ou plusieurs contraintes de validation ne sont pas respectées.", 
					  "constraint_violation", "nolink", null);
	  
	  errorResponse.setValidationError(new Result(exception.getConstraintViolations()));
	  
      return Response
    		  .status(Status.BAD_REQUEST)
    		  .entity(errorResponse)
    		  .build();  
	 
  }

}