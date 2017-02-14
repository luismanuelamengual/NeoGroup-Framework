
package org.neogroup.sparks.templating;

public class TemplateNotFoundException extends TemplateException {
    public TemplateNotFoundException() {
    }

    public TemplateNotFoundException(String message) {
        super(message);
    }

    public TemplateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateNotFoundException(Throwable cause) {
        super(cause);
    }
}
