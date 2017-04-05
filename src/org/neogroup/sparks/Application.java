
package org.neogroup.sparks;

import org.neogroup.sparks.views.ViewsManager;
import org.neogroup.sparks.views.freemarker.FreeMarkerViewFactory;
import org.neogroup.sparks.views.velocity.VelocityViewFactory;
import org.neogroup.util.Properties;
import org.neogroup.util.BundlesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends ApplicationContext {

    private final static String LOGGER_NAME = "sparks_logger";
    private final static String PROPERTIES_RESOURCE_NAME = "app.properties";
    private final static String DEFAULT_BUNDLE_NAME_PROPERTY = "default_bundle_name";
    private final static String BUNDLES_PATH_PROPERTY = "bundles_path";

    protected final List<Module> modules;

    public Application () {

        //Propiedades de la aplicación
        properties = new Properties();
        try {
            properties.loadFromFile(PROPERTIES_RESOURCE_NAME);
        }
        catch (Exception ex) {}

        //Traductor de la aplicación
        bundlesManager = new BundlesManager();
        String bundlesPath = properties.get(BUNDLES_PATH_PROPERTY);
        if (bundlesPath != null) {
            bundlesManager.setDefaultBundlesPath(bundlesPath);
        }
        String defaultBundleName = properties.get(DEFAULT_BUNDLE_NAME_PROPERTY);
        if (defaultBundleName != null) {
            bundlesManager.setDefaultBundleName(defaultBundleName);
        }

        //Logger de la aplicación
        logger = Logger.getLogger(LOGGER_NAME);

        //Manejador de templates
        VelocityViewFactory velocityTemplateFactory = new VelocityViewFactory();
        velocityTemplateFactory.setDebugMode(true);
        FreeMarkerViewFactory freeMarkerTemplateFactory = new FreeMarkerViewFactory();
        viewsManager = new ViewsManager();
        viewsManager.addViewFactory(velocityTemplateFactory);
        viewsManager.addViewFactory(freeMarkerTemplateFactory);

        //Modulos de la aplicación
        modules = new ArrayList<>();
    }

    public final void addModule(Module module) {
        modules.add(module);
    }

    public final void removeModule(Module module) {
        modules.remove(module);
    }

    public final void start() {
        startContext();
    }

    public final void stop() {
        stopContext();
    }

    @Override
    protected void onStart() {
        for (Module module : modules) {
            module.startContext();
        }
    }

    @Override
    protected void onStop() {
        for (Module module : modules) {
            module.stopContext();
        }
    }
}