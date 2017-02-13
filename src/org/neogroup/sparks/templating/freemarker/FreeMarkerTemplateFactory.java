
package org.neogroup.sparks.templating.freemarker;

import org.neogroup.sparks.templating.TemplateFactory;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;

public class FreeMarkerTemplateFactory extends TemplateFactory<FreeMarkerTemplate> {

    private final Configuration configuration;

    public FreeMarkerTemplateFactory(String basePath) {
        super(basePath);
        try {
            configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setDirectoryForTemplateLoading(new File(basePath));
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error en la inicialización de templates freemarker !!");
        }
    }

    @Override
    public FreeMarkerTemplate createTemplate(String filename) {
        return new FreeMarkerTemplate(configuration, filename);
    }
}
