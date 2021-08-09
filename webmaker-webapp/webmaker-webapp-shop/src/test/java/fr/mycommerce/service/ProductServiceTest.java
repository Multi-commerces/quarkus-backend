package fr.mycommerce.service;

import static org.junit.Assert.assertNotNull;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import fr.webmaker.resource.ProductRestClient;

/**
 * Annotate the class with @MicroShedTest
 * @author Julien ILARI 2020-09
 *
 */
@MicroShedTest
public class ProductServiceTest
{

	/**
	 * Create a public static ApplicationContainer field
	 */
	@Container
    public static ApplicationContainer app = new ApplicationContainer()
                    .withAppContextRoot("/");
 
	/**
	 * static JAX-RS resource classes
	 */
	@RestClient
	public static ProductRestClient resource;
    
	/**
	 * Inject one or more public static JAX-RS resource classes
	 */
	@Test
    public void testResource() {
    	assertNotNull(resource);
    }

}
