
package org.neogroup.sparks.views;

public class ViewNotFoundException extends ViewException {
    public ViewNotFoundException() {
    }

    public ViewNotFoundException(String message) {
        super(message);
    }

    public ViewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewNotFoundException(Throwable cause) {
        super(cause);
    }
}
