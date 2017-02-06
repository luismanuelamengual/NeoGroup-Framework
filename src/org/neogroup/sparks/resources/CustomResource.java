
package org.neogroup.sparks.resources;

import java.util.HashMap;
import java.util.Map;

public class CustomResource extends Resource {

    private final Map<String, Object> properties;

    public CustomResource() {
        this.properties = new HashMap<>();
    }

    public void setProperty (String property, Object value) {
        properties.put(property, value);
    }

    public Object getProperty (String property) {
        return properties.get(property);
    }
}
