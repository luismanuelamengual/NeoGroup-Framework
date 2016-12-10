
package org.neogroup.sparks.web.http.contexts;

import java.util.List;

import org.neogroup.sparks.web.http.HttpMethod;
import org.neogroup.sparks.web.http.HttpRequest;
import org.neogroup.sparks.web.http.HttpResponse;

public abstract class RestContext<T> extends Context {

    public RestContext(String path) {
        super(path);
    }

    @Override
    public void onContext(HttpRequest request, HttpResponse response) {
        
        String method = request.getMethod();
        
        List<T> resources = null;
        switch (method) {
            case HttpMethod.GET:
                resources = getResources(request);
                break;
            case HttpMethod.PUT:
                resources = createResources(request, decodeResources(request.getBody()));
                break;
            case HttpMethod.POST:
                resources = updateResources(request, decodeResources(request.getBody()));
                break;
            case HttpMethod.DELETE:  
                resources = deleteResources(request);
                break;
        }

        if (resources != null && !resources.isEmpty()) {
            response.setBody(encodeResources(resources));
        }
    }
    
    protected abstract List<T> decodeResources (byte[] content);
    protected abstract byte[] encodeResources (List<T> resource);
    
    protected abstract List<T> getResources (HttpRequest request);
    protected abstract List<T> createResources (HttpRequest request, List<T> resources);
    protected abstract List<T> updateResources (HttpRequest request, List<T> resources);
    protected abstract List<T> deleteResources (HttpRequest request);
}