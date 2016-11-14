
package org.neogroup.websparks;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.neogroup.websparks.http.HttpServer;
import org.neogroup.websparks.properties.Properties;

public class Application {
    
    private static final String LOGGER_NAME = "WebSparks";
    private static final String APP_PROPERTIES_FILENAME = "app.properties";
    private static final String LOG_PROPERTIES_FILENAME = "log.properties";
    
    private final HttpServer server;
    private final Properties properties;
    private final Logger logger;
    
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
        
        int port = properties.getInt(Properties.SERVER_PORT_PROPERTY_NAME, 80);
        int maxThreads = properties.getInt(Properties.SERVER_MAX_THREADS_PROPERTY_NAME);
        try {
            this.server = new HttpServer(port, maxThreads);
        }
        catch (IOException ex) {
            throw new RuntimeException("Unable to execute web server !!", ex);
        }
    }

    public final Properties getProperties() {
        return properties;
    }

    public final Logger getLogger() {
        return logger;
    }
}