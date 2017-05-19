
package example;

import example.processors.*;
import org.neogroup.sparks.Application;
import org.neogroup.sparks.views.velocity.VelocityViewFactory;
import org.neogroup.sparks.web.WebModule;
import org.postgresql.ds.PGPoolingDataSource;

public class Main {

    public static void main(String[] args) {

        Application application = new Application();
        application.loadPropertiesFromResource("app.properties");

        WebModule module1 = new WebModule(application, 1408);
        module1.registerProcessor(RamaProcessor.class);

        WebModule module2 = new WebModule(application, 1409);
        module2.registerProcessor(PepeProcessor.class);

        //Load web module
        application.addModule(module1);
        application.addModule(module2);

        //Add view factories
        application.addViewFactory("velocity", new VelocityViewFactory());

        //Add data sources
        PGPoolingDataSource postgreDataSource = new PGPoolingDataSource();
        postgreDataSource.setServerName("localhost");
        postgreDataSource.setDatabaseName("testdb");
        postgreDataSource.setUser("postgres");
        postgreDataSource.setPassword("postgres");
        application.addDataSource("main", postgreDataSource);

        //Register processors
        application.registerProcessors(
                TestProcessor.class,
                UserCRUDProcessor.class,
                UsersProcessor.class,
                PersonCRUDProcessor.class,
                PersonsProcessor.class,
                VaneProcessor.class
        );
        application.start();
    }
}