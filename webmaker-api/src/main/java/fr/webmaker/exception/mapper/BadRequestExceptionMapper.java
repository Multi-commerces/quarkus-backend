package fr.webmaker.exception.mapper;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.webmaker.restfull.model.ErrorResponse;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> 
{
    @Override
    public Response toResponse(BadRequestException exception) 
    {
    	ErrorResponse errorResponse = new ErrorResponse(String.valueOf(exception.getResponse().getStatus()), exception.getMessage(), "request_invalid", "nolink", null);
        return Response.status(exception.getResponse().getStatus()).entity(errorResponse).build();  
    }
}