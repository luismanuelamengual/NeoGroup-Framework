
package org.neogroup.net.httpserver;

import java.util.List;

public class HttpHeader {

    private static final String HEADER_ASSIGNATION = ":";
    private static final String HEADER_VALUES_SEPARATOR = ";";
    
    private final String name;
    private final String value;

    public HttpHeader(String name, List<String> values) {
        this.name = name;
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                valueBuilder.append(HEADER_VALUES_SEPARATOR);
            }
            valueBuilder.append(values.get(i));
        }
        this.value = valueBuilder.toString();
    }
    
    public HttpHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append(HEADER_ASSIGNATION).append(" ");
        result.append(value);
        return result.toString();
    }
}
