
package org.neogroup.sparks.templating.velocity;

import org.neogroup.sparks.templating.TemplateFactory;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityTemplateFactory extends TemplateFactory<VelocityTemplate> {

    static {
        Velocity.init();
    }

    private final VelocityEngine engine;

    public VelocityTemplateFactory(String basePath) {
        super(basePath);
        engine = new VelocityEngine();
        engine.setProperty("file.resource.loader.path", basePath);
        engine.setProperty("file.resource.loader.cache", "false");
        engine.setProperty("velocimacro.library.autoreload", "true");
    }

    @Override
    public VelocityTemplate createTemplate(String filename) {
        return new VelocityTemplate(engine, filename);
    }
}
