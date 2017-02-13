
package org.neogroup.sparks.templating.freemarker;

import org.neogroup.sparks.templating.Template;
import org.neogroup.sparks.templating.TemplateExtensions;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@TemplateExtensions({"ft"})
public class FreeMarkerTemplate extends Template {

    private static String TEMPLATE_PROCESSING_ERROR = "Error processing freemarker template !!";

    private final Configuration configuration;
    private final Map<String,Object> parameters;

    public FreeMarkerTemplate(Configuration configration, String filename) {
        super(filename);
        this.configuration = configration;
        this.parameters = new HashMap<>();
    }

    @Override
    public void setParameter(String name, Object value) {
        parameters.put(name, value);
    }

    @Override
    public Object getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public String render() {
        String response = null;
        try (StringWriter writer = new StringWriter()) {
            configuration.getTemplate(getFilename()).process(parameters, writer);
            response = writer.toString();
        }
        catch (Throwable throwable) {
            throw new RuntimeException(TEMPLATE_PROCESSING_ERROR, throwable);
        }
        return response;
    }
}
