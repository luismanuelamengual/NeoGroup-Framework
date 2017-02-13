
package org.neogroup.sparks.templating;

public abstract class TemplateFactory<T extends Template> {

    private final String basePath;

    public TemplateFactory(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }

    public abstract T createTemplate(String filename);
}
