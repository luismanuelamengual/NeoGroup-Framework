package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

public abstract class WebProcessor extends Processor<WebCommand> {

    @Override
    public Object execute(WebCommand command) {

        onRequest(command.getRequest(), command.getResponse());
        return true;
    }

    protected abstract void onRequest (HttpRequest request, HttpResponse response);
}