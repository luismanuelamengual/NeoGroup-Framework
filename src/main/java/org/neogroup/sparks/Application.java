
package org.neogroup.sparks;

import org.neogroup.sparks.processors.crud.CRUDSelectorProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application for sparks
 */
public class Application extends ApplicationContext {

    protected final List<Module> modules;

    /**
     * Default constructor for the application
     */
    public Application () {
        modules = new ArrayList<>();
        registerProcessor(CRUDSelectorProcessor.class);
    }

    /**
     * Adds a new module to the application
     * @param module Module to add
     */
    public final void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Removes a module from the application
     * @param module Module to remove
     */
    public final void removeModule(Module module) {
        modules.remove(module);
    }

    /**
     * Get registered modules for the application
     * @return list of modules
     */
    public final List<Module> getModules () {
        return modules;
    }

    /**
     * Method that is executed when the application is started
     * All modules are started
     */
    @Override
    protected void onStart() {
        for (Module module : modules) {
            module.start();
        }
    }

    /**
     * Method that is executed when the application is stopped
     * All modules are stopped
     */
    @Override
    protected void onStop() {
        for (Module module : modules) {
            module.stop();
        }
    }
}