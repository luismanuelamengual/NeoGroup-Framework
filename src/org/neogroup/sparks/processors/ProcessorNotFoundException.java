
package org.neogroup.sparks.processors;

public class ProcessorNotFoundException extends RuntimeException {

    public ProcessorNotFoundException(String s) {
        super(s);
    }

    public ProcessorNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
