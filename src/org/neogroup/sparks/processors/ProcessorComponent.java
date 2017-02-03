
package org.neogroup.sparks.processors;

import org.neogroup.sparks.commands.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessorComponent {

    public boolean statefull() default true;
    public Class<? extends Command>[] commands() default {};
}

