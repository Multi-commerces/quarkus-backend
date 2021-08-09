package fr.mycommerce;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianExempleIT
{

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
    	return ArquillianTool.createDeployment()
    		.addPackages(true, ArquillianExempleIT.class.getPackage());
    }
	


}
