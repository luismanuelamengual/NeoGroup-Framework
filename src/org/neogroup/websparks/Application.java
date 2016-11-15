
package org.neogroup.websparks;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.neogroup.websparks.controllers.Controller;
import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.http.contexts.Context;
import org.neogroup.websparks.http.contexts.ControllersContext;
import org.neogroup.websparks.properties.Properties;

public class Application {
    
    private static final int DEFAULT_PORT = 1408;
    private static final int DEFAULT_MAX_THREADS = 0;
    
    private static final String LOGGER_NAME = "WebSparks";
    private static final String APP_PROPERTIES_FILENAME = "app.properties";
    private static final String LOG_PROPERTIES_FILENAME = "log.properties";
    
    private final HttpServer server;
    private final Properties properties;
    private final Logger logger; 
    private final ControllersContext controllersContext;
    private final Map<Class<? extends Controller>, Controller> controllers;
    
    public Application() {
        
        properties = new Properties();
        try {
            properties.loadResource(APP_PROPERTIES_FILENAME);
        } 
        catch (IOException ex) {
        }
        
        logger = Logger.getLogger(LOGGER_NAME);
        try {
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(LOG_PROPERTIES_FILENAME);
            if (resourceStream != null) {
                LogManager.getLogManager().readConfiguration(resourceStream);
                resourceStream.close();
            }
        }
        catch (IOException ex) {
        }
        
        controllers = new HashMap<>();
        
        int port = properties.getInt(Properties.SERVER_PORT_PROPERTY_NAME, DEFAULT_PORT);
        int maxThreads = properties.getInt(Properties.SERVER_MAX_THREADS_PROPERTY_NAME, DEFAULT_MAX_THREADS);
        try {
            this.server = new HttpServer(port, maxThreads);
        }
        catch (IOException ex) {
            throw new RuntimeException("Unable to execute web server !!", ex);
        }
        
        controllersContext = new ControllersContext();
        server.addContext(controllersContext);
    }

    public final Properties getProperties() {
        return properties;
    }

    public final Logger getLogger() {
        return logger;
    }
    
    public final void addContext (Context context) {
        server.addContext(context);
    }
    
    public final void registerController (Class<? extends Controller> controllerClass) {
        
        Controller controller = null;
        try {
            controller = controllerClass.getDeclaredConstructor(Application.class).newInstance(this);
        }
        catch (Exception ex) {}
        
        if (controller != null) {
            controllers.put(controllerClass, controller);
            controllersContext.registerController(controller);
        }
    }
    
    public final Controller getController (Class<? extends Controller> controllerClass) {
        return controllers.get(controllerClass);
    }
    
    public final void start () {
        server.start();
    }
    
    public final void stop () {
        server.stop();
    }
}