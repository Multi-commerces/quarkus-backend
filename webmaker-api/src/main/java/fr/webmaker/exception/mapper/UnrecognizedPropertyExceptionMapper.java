package fr.webmaker.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<PropertyBindingException> 
{
    @Override
    public Response toResponse(PropertyBindingException exception) 
    {
    	ErrorResponse errorResponse = new ErrorResponse("400", 
    			exception.getPropertyName() + " n'est pas reconnu et les données ne peuvent pas être enregistrées", 
    			"validation_error", "nolink", null);
    	
        return Response.status(Status.BAD_REQUEST).entity(errorResponse).build();  
    }
}