
package org.neogroup.sparks.templating.velocity;

import org.neogroup.sparks.templating.Template;
import org.neogroup.sparks.templating.TemplateExtensions;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

@TemplateExtensions({"vm"})
public class VelocityTemplate extends Template {

    private static String TEMPLATE_PROCESSING_ERROR = "Error processing velocity template !!";

    private final VelocityContext context;
    private final VelocityEngine engine;

    public VelocityTemplate(VelocityEngine engine, String filename) {
        super(filename);
        this.engine = engine;
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
            engine.getTemplate(getFilename()).merge(context, writer);
            response = writer.toString();
        }
        catch (Throwable throwable) {
            throw new RuntimeException(TEMPLATE_PROCESSING_ERROR, throwable);
        }
        return response;
    }
}
