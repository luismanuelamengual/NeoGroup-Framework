
package org.neogroup.sparks.views.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.neogroup.sparks.views.ViewException;
import org.neogroup.sparks.views.ViewFactory;
import org.neogroup.sparks.views.ViewNotFoundException;

import java.io.File;

public class FreeMarkerViewFactory extends ViewFactory<FreeMarkerView> {

    public static final String TEMPLATE_NAMESPACE_SEPARATOR = ".";

    private final Configuration configuration;

    public FreeMarkerViewFactory() {
        configuration = new Configuration(Configuration.VERSION_2_3_25);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        configuration.setLogTemplateExceptions(false);
    }

    public void setBasePath (String basePath) {
        try {
            configuration.setDirectoryForTemplateLoading(new File(basePath));
        } catch (Exception ex) {
            throw new RuntimeException("Error setting freemarker basePath !!");
        }
    }

    @Override
    public FreeMarkerView createView(String viewName) throws ViewException {
        try {
            String templateFilename = viewName.replace(TEMPLATE_NAMESPACE_SEPARATOR, File.separator) + ".ft";
            Template template = configuration.getTemplate(templateFilename);
            return new FreeMarkerView(template);
        }
        catch (freemarker.template.TemplateNotFoundException ex) {
            throw new ViewNotFoundException(ex);
        }
        catch (Exception ex) {
            throw new ViewException(ex);
        }
    }
}
