package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;

/**
 * Indicates that annotated class is a <em>WebControllerAdapter</em>.
 *
 * <p>
 * Represents a web controller adapter component. This annotation serves as a
 * specialization of Spring's {@link @Controller}, allowing for
 * implementation classes to be auto-detected through classpath scanning by
 * Spring.
 * </p>
 *
 * <p>
 * Web controller adapters are the classes that expose endpoints to the
 * application's clients.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface WebControllerAdapter {
    @AliasFor(annotation = Controller.class)
    String value() default "";
}
