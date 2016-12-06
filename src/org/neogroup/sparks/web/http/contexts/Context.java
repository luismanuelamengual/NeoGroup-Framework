
package org.neogroup.sparks.web.http.contexts;

import org.neogroup.sparks.web.http.HttpRequest;
import org.neogroup.sparks.web.http.HttpResponse;

public abstract class Context {

    private final String path;

    public Context(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public abstract void onContext (HttpRequest request, HttpResponse response);
}