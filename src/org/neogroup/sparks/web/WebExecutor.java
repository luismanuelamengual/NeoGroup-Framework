package org.neogroup.sparks.web;

import org.neogroup.sparks.Executor;
import org.neogroup.sparks.web.http.HttpResponse;
import org.neogroup.sparks.web.http.HttpRequest;

public abstract class WebExecutor extends Executor<WebAction> {

    @Override
    public Object execute(WebAction action) {

        onRequest(action.getRequest(), action.getResponse());
        return true;
    }

    protected abstract void onRequest (HttpRequest request, HttpResponse response);
}