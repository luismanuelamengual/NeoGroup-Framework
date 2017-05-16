
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate the commands that may be
 * executed by a processor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessorComponent {

    /**
     * List of commands associated to the processor
     * @return array of command classes
     */
    public Class<? extends Command>[] commands() default {};
}

