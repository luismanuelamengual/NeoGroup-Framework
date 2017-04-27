
package org.neogroup.sparks.views.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.neogroup.sparks.views.View;

import java.io.StringWriter;

public class VelocityView extends View {

    private static String TEMPLATE_PROCESSING_ERROR = "Error processing velocity template !!";

    private final Template template;
    private final VelocityContext context;

    public VelocityView(Template template) {
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
