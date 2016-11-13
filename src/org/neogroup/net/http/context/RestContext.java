
package org.neogroup.net.http.context;

import java.util.List;
import org.neogroup.net.http.HttpRequest;
import org.neogroup.net.http.HttpResponse;

public abstract class RestContext<T> extends Context {

    public RestContext(String path) {
        super(path);
    }

    @Override
    public HttpResponse onContext(HttpRequest request) {
        
        String method = request.getMethod();
        
        List<T> resources = null;
        switch (method) {
            case HttpRequest.METHOD_GET:  
                resources = getResources(request);
                break;
            case HttpRequest.METHOD_PUT:
                resources = createResources(request, decodeResources(request.getBody()));
                break;
            case HttpRequest.METHOD_POST:
                resources = updateResources(request, decodeResources(request.getBody()));
                break;
            case HttpRequest.METHOD_DELETE:  
                resources = deleteResources(request);
                break;
        }
        
        HttpResponse response = new HttpResponse();
        if (resources != null && !resources.isEmpty()) {
            response.setBody(encodeResources(resources));
        }
        return response;
    }
    
    protected abstract List<T> decodeResources (byte[] content);
    protected abstract byte[] encodeResources (List<T> resource);
    
    protected abstract List<T> getResources (HttpRequest request);
    protected abstract List<T> createResources (HttpRequest request, List<T> resource);
    protected abstract List<T> updateResources (HttpRequest request, List<T> resource);
    protected abstract List<T> deleteResources (HttpRequest request);
}