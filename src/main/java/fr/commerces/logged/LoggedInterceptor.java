package fr.commerces.logged;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logged Interceptor
 * <p>
 * capture des exceptions afin d'enrichir le log pour la résolution d'anomalie.
 * </p>
 * 
 * @author Julien ILARI
 *
 */
@Logged
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggedInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class);

	public LoggedInterceptor() {

	}

	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		logger.info("Entering method: " + invocationContext.getMethod().getName() + " in class "
				+ invocationContext.getMethod().getDeclaringClass().getName());

		try {
			return invocationContext.proceed();
		} catch (PersistenceException e) {
			logger.error("L'action n'a pas pu aboutir en raison d'une violation de contrainte");
			throw e;
		}

	}
	
}