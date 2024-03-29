package fr.webmaker.exception.business;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> 
{
    @Override
    public Response toResponse(BusinessException exception) 
    {
    	ErrorResponse errorResponse = new ErrorResponse("400", exception.getMessage(), "error_metier", "nolink", null);
        return Response.status(Status.BAD_REQUEST).entity(errorResponse).build();  
    }
}