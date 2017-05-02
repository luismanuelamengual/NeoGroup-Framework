
package org.neogroup.sparks;

public class Manager {

    protected final ApplicationContext applicationContext;

    public Manager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void onStart () {}

    public void onStop () {}
}
