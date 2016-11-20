
package org.neogroup.websparks.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Properties extends java.util.Properties {

    public static final String MAIN_PROPERTIES_FILE = "websparks.properties";

    public static final String SERVER_NAME_PROPERTY = "server_name";

    private static final Properties instance;

    static {
        instance = new Properties();
        try {
            loadResource(MAIN_PROPERTIES_FILE);
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading main properties file !!");
        }
    }

    private Properties () {

    }

    public static void loadResource (String resourceName) throws IOException {
        
        InputStream in = Properties.class.getClassLoader().getResourceAsStream(resourceName);
        if (in != null) {
            instance.load(in);
            in.close();
        } 
        else {
            throw new FileNotFoundException("Property file \"" + resourceName + "\" not found !!");
        }
    }

    public static void set(String key, String value) {
        instance.put(key, value);
    }

    public static String get (String key) {
        return get(key, "");
    }

    public static String get (String key, String defaultValue) {
        String value = instance.getProperty(key);
        return value != null? value : defaultValue;
    }

    public static int getInt (String key) {
        return getInt(key, 0);
    }
    
    public static int getInt (String key, int defaultValue) {
        String value = instance.getProperty(key);
        return value != null? Integer.parseInt(value) : defaultValue;
    }

    public static double getDouble (String key) {
        return getDouble(key, 0);
    }

    public static double getDouble (String key, double defaultValue) {
        String value = instance.getProperty(key);
        return value != null? Double.parseDouble(value) : defaultValue;
    }
}