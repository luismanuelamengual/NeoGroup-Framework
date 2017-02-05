
package org.neogroup.sparks.processors;

public class ProcessorException extends RuntimeException {

    public ProcessorException(String s) {
        super(s);
    }

    public ProcessorException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
