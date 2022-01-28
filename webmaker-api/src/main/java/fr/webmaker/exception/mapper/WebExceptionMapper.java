package fr.webmaker.exception.mapper;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class WebExceptionMapper implements ExceptionMapper<ClientErrorException> 
{
    @Override
    public Response toResponse(ClientErrorException exception) 
    {
    	ErrorResponse errorResponse = new ErrorResponse(String.valueOf(exception.getResponse().getStatus()), exception.getMessage(), "error_technical", "nolink", null);
        return Response.status(exception.getResponse().getStatus()).entity(errorResponse).build();  
    }
}