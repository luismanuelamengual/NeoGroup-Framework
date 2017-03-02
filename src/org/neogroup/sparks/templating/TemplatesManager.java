
package org.neogroup.sparks.templating;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

public class TemplatesManager {

    private static final String TEMPLATE_NOT_FOUND_ERROR = "Template \"{0}\" not found !!";

    private final Set<TemplateFactory> templateFactories;

    public TemplatesManager() {
        this.templateFactories = new HashSet<>();
    }

    public void addTemplateFactory(TemplateFactory templateFactory) {
        templateFactories.add(templateFactory);
    }

    public void removeTemplateFactory(TemplateFactory templateFactory) {
        templateFactories.remove(templateFactory);
    }

    public Template createTemplate (String templateName) throws TemplateException {

        Template template = null;
        if (templateName != null && !templateName.isEmpty()) {
            for (TemplateFactory templateFactory : templateFactories) {
                try {
                    template = templateFactory.createTemplate(templateName);
                    if (template != null) {
                        break;
                    }
                } catch (TemplateNotFoundException ex) {
                }
            }
        }
        if (template == null) {
            throw new TemplateNotFoundException(MessageFormat.format(TEMPLATE_NOT_FOUND_ERROR, templateName));
        }
        return template;
    }
}
