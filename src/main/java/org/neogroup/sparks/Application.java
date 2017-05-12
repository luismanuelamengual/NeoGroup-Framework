
package org.neogroup.sparks;

import org.neogroup.sparks.processors.crud.CRUDSelectorProcessor;

import java.util.*;
import java.util.logging.Logger;

public class Application extends ApplicationContext {

    protected final List<Module> modules;

    public Application () {
        modules = new ArrayList<>();
        registerProcessor(CRUDSelectorProcessor.class);
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