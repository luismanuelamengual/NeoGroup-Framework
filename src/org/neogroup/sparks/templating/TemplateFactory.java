
package org.neogroup.sparks.templating;

public abstract class TemplateFactory<T extends Template> {

    public abstract T createTemplate(String templateName) throws TemplateException;
}
