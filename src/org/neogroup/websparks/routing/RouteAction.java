
package org.neogroup.websparks.routing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.neogroup.websparks.http.HttpRequest;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RouteAction {

    public String name() default "";
    
    public String method() default HttpRequest.METHOD_GET;
}
