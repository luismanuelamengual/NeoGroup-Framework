
package org.neogroup.websparks.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Properties extends java.util.Properties {
    
    public static final String SERVER_PORT_PROPERTY_NAME = "server_port";
    public static final String SERVER_MAX_THREADS_PROPERTY_NAME = "server_maxThreads";
    
    public void loadResource (String resourceName) throws IOException {
        
        InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);    
        if (in != null) {
            load(in);
            in.close();
        } 
        else {
            throw new FileNotFoundException("Property file \"" + resourceName + "\" not found !!");
        }
    }
    
    public int getInt (String key) {
        return getInt(key, 0);
    }
    
    public int getInt (String key, int defaultValue) {
        String value = this.getProperty(key);
        return value != null? Integer.parseInt(value) : defaultValue;
    }
}