
package org.neogroup.sparks.templating.velocity;

import org.apache.velocity.VelocityContext;
import org.neogroup.sparks.templating.Template;

import java.io.StringWriter;

public class VelocityTemplate extends Template {

    private static String TEMPLATE_PROCESSING_ERROR = "Error processing velocity template !!";

    private final org.apache.velocity.Template template;
    private final VelocityContext context;

    public VelocityTemplate(org.apache.velocity.Template template) {
        this.template = template;
        this.context = new VelocityContext();
    }

    @Override
    public void setParameter(String name, Object value) {
        context.put(name, value);
    }

    @Override
    public Object getParameter(String name) {
        return context.get(name);
    }

    @Override
    public String render() {
        String response = null;
        try (StringWriter writer = new StringWriter()) {
            template.merge(context, writer);
            response = writer.toString();
        }
        catch (Throwable throwable) {
            throw new RuntimeException(TEMPLATE_PROCESSING_ERROR, throwable);
        }
        return response;
    }
}
