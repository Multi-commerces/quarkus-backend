package fr.webmaker;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.mycommerce.ArquillianTool;
import fr.mycommerce.view.product.ProductBasicMB;

@RunWith(Arquillian.class)
public class ExempleArquillianIT
{
    
    @Deployment
    public static WebArchive createDeployment() {
    	return ArquillianTool.createDeployment()
    			.addClass(ExempleArquillianIT.class);
    }
	

    @ArquillianResource
    private URL baseURL;

    @Inject
    ProductBasicMB managedBean;


    @Test
    public void testInventoryEndpoints() throws Exception {
       
        assertNotNull(managedBean);
        managedBean.callServiceFindById("1");
    }

}
