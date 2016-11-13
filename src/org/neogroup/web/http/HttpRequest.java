
package org.neogroup.web.http;

import java.net.URI;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    
    private static final String URI_SEPARATOR = "/";
    
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";
    
    private final String method;
    private final List<HttpHeader> headers;
    private final URI uri;
    private final byte[] body;
 
    public HttpRequest(String method, List<HttpHeader> headers, URI uri, byte[] body) {
        this.method = method;
        this.headers = headers;
        this.uri = uri;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public byte[] getBody() {
        return body;
    }
    
    public URI getUri() {
        return uri;
    }

    public List<HttpHeader> getHeaders() {
        return Collections.unmodifiableList(headers);
    }
    
    public String getQuery() {
        return uri.getRawQuery();
    }
    
    public String getPath() {
        return uri.getRawPath();
    }
    
    public List<String> getPathParts() {
        String path = getPath();
        String[] pathTokens = path.split(URI_SEPARATOR);
        return Arrays.asList(pathTokens);
    }
    
    public Map<String,String> getParameters() {
    
        Map<String,String> parameters = new HashMap<>();
        try {
            String query = uri.getRawQuery();
            if (query != null) {
                String pairs[] = query.split("[&]");
                for (String pair : pairs) {
                    String param[] = pair.split("[=]");
                    String key = null;
                    String value = null;
                    if (param.length > 0) {
                        key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
                    }
                    if (param.length > 1) {
                        value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
                    }
                    parameters.put(key, value);
                }
            }
        }
        catch (Exception ex) {}
        return parameters;
    }
}