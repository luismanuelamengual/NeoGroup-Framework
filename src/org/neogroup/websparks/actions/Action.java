
package org.neogroup.websparks.actions;

import org.neogroup.websparks.Application;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Action {

    protected final Application application;

    public Action(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void execute () throws Exception {

        try {
            onAction();
        }
        catch (Throwable ex) {
            try {
                onError(ex);
            }
            catch (Throwable error) {
                throw ex;
            }
        }
    }

    protected void onError (Throwable error) throws Exception
    {
        throw new NotImplementedException();
    }

    public abstract void onAction () throws Exception;
}