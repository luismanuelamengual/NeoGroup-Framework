
package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;
import org.neogroup.websparks.http.contexts.HttpContextInstance;

public abstract class Controller {

    protected HttpRequest getRequest() {
        return HttpContextInstance.getInstance().getRequest();
    }

    protected HttpResponse getResponse() {
        return HttpContextInstance.getInstance().getResponse();
    }

    protected void setResponseCode (int responseCode) {
        getResponse().setResponseCode(responseCode);
    }

    protected void setResponseContents (String contents) {
        getResponse().setBody(contents);
    }

    protected void write (String text) {
        getResponse().write(text);
    }

    protected void flush () {
        getResponse().flush();
    }
}