package fr.commerces;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.flywaydb.core.Flyway;

@ApplicationScoped
public class MigrationService {
    // You can Inject the object if you want to use it manually
    @Inject
    Flyway flyway; 



    public void checkMigration() {
        // Use the flyway instance manually
        flyway.clean(); 
        flyway.migrate();
        // This will print 1.0.0
        System.out.println(flyway.info().current().getVersion().toString());
    }
}