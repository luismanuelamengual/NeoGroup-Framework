
package org.neogroup.sparks.templating.freemarker;

import org.neogroup.sparks.templating.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerTemplate extends Template {

    private static String TEMPLATE_PROCESSING_ERROR = "Error processing freemarker template !!";

    private final freemarker.template.Template template;
    private final Map<String,Object> parameters;

    public FreeMarkerTemplate(String filename, freemarker.template.Template template) {
        super(filename);
        this.template = template;
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
            template.process(parameters, writer);
            response = writer.toString();
        }
        catch (Throwable throwable) {
            throw new RuntimeException(TEMPLATE_PROCESSING_ERROR, throwable);
        }
        return response;
    }
}
