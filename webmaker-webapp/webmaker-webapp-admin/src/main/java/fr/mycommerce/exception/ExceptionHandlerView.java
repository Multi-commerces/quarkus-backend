package fr.mycommerce.exception;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.inject.Named;



@RequestScoped
@Named("exceptionHandlerView")
public class ExceptionHandlerView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void throwNullPointerException() {
        throw new NullPointerException("A NullPointerException!");
    }

    public void throwWrappedIllegalStateException() {
        Throwable t = new IllegalStateException("A wrapped IllegalStateException!");
        throw new FacesException(t);
    }

    public void throwViewExpiredException() {
        throw new ViewExpiredException("A ViewExpiredException!",
                FacesContext.getCurrentInstance().getViewRoot().getViewId());
    }
}