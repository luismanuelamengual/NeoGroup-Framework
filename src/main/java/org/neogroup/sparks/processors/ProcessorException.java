
package org.neogroup.sparks.processors;

/**
 * Processor exception
 */
public class ProcessorException extends RuntimeException {

    /**
     * Constructor for the processor exception
     * @param s message
     */
    public ProcessorException(String s) {
        super(s);
    }

    /**
     * Constructor for the processor exception
     * @param s message
     * @param throwable error
     */
    public ProcessorException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
