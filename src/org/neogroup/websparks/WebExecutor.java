package org.neogroup.websparks;

import org.neogroup.websparks.http.HttpRequest;
import org.neogroup.websparks.http.HttpResponse;

public abstract class WebExecutor extends Executor<WebAction> {

    @Override
    public Object execute(WebAction command) {

        onRequest(command.getRequest(), command.getResponse());
        return true;
    }

    protected abstract void onRequest (HttpRequest request, HttpResponse response);
}