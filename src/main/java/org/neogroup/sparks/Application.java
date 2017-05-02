
package org.neogroup.sparks;

import org.neogroup.sparks.processors.crud.CRUDSelectorProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends ApplicationContext {

    private final static String LOGGER_NAME = "sparks_logger";
    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";

    protected final List<Module> modules;

    public Application () {

        //Propiedades de la aplicación
        properties = new Properties();
        try {
            properties.loadFromFile(PROPERTIES_RESOURCE_NAME);
        }
        catch (Exception ex) {}

        //Logger de la aplicación
        logger = Logger.getLogger(LOGGER_NAME);

        //Modulos de la aplicación
        modules = new ArrayList<>();

        processorsManager.registerProcessor(CRUDSelectorProcessor.class);
    }

    public final void addModule(Module module) {
        modules.add(module);
    }

    public final void removeModule(Module module) {
        modules.remove(module);
    }

    @Override
    protected void onStart() {
        for (Module module : modules) {
            module.start();
        }
    }

    @Override
    protected void onStop() {
        for (Module module : modules) {
            module.stop();
        }
    }
}