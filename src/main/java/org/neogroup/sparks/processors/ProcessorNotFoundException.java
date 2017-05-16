
package org.neogroup.sparks.processors;

/**
 * Exception to indicate a processor was not found
 * in the framework
 */
public class ProcessorNotFoundException extends RuntimeException {

    /**
     * Constructor for the processor exception
     * @param s message
     */
    public ProcessorNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructor for the processor exception
     * @param s message
     * @param throwable error
     */
    public ProcessorNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
