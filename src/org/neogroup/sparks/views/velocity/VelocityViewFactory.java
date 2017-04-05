
package org.neogroup.sparks.views.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.neogroup.sparks.views.ViewException;
import org.neogroup.sparks.views.ViewFactory;
import org.neogroup.sparks.views.ViewNotFoundException;

import java.io.File;

public class VelocityViewFactory extends ViewFactory<VelocityView> {

    public static final String TEMPLATE_NAMESPACE_SEPARATOR = ".";

    private final VelocityEngine engine;

    public VelocityViewFactory() {
        engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
        engine.setProperty(RuntimeConstants.RUNTIME_LOG, "/dev/null");
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    }

    public void setBasePath (String basePath) {
        engine.setProperty("file.resource.loader.path", basePath);
    }

    public void setDebugMode (boolean debugMode) {
        engine.setProperty("file.resource.loader.cache", debugMode?"false":"true");
        engine.setProperty("velocimacro.library.autoreload", debugMode?"true":"false");
        engine.setProperty("resource.manager.cache.enabled", false);
        engine.setProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL, true);
    }

    @Override
    public VelocityView createView(String viewName) throws ViewException {
        try {
            String templateFilename = viewName.replace(TEMPLATE_NAMESPACE_SEPARATOR, File.separator) + ".vm";
            Template template = engine.getTemplate(templateFilename);
            return new VelocityView(template);
        }
        catch (ResourceNotFoundException ex) {
            throw new ViewNotFoundException(ex);
        }
        catch (Exception ex) {
            throw new ViewException(ex);
        }
    }
}
