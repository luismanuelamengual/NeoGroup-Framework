
package org.neogroup.sparks.commands;

import org.neogroup.sparks.processors.DefaultProcessorSelector;
import org.neogroup.sparks.processors.ProcessorSelector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandComponent {

    public Class<? extends ProcessorSelector> selector() default DefaultProcessorSelector.class;
}
