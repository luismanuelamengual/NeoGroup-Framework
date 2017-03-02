
package org.neogroup.sparks.templating.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.neogroup.sparks.templating.TemplateException;
import org.neogroup.sparks.templating.TemplateFactory;
import org.neogroup.sparks.templating.TemplateNotFoundException;

import java.io.File;

public class FreeMarkerTemplateFactory extends TemplateFactory<FreeMarkerTemplate> {

    public static final String TEMPLATE_NAMESPACE_SEPARATOR = ".";

    private final Configuration configuration;

    public FreeMarkerTemplateFactory() {
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
    public FreeMarkerTemplate createTemplate(String templateName) throws TemplateException {
        try {
            String templateFilename = templateName.replace(TEMPLATE_NAMESPACE_SEPARATOR, File.separator) + ".ft";
            Template template = configuration.getTemplate(templateFilename);
            return new FreeMarkerTemplate(templateFilename, template);
        }
        catch (freemarker.template.TemplateNotFoundException ex) {
            throw new TemplateNotFoundException(ex);
        }
        catch (Exception ex) {
            throw new TemplateException (ex);
        }
    }
}
