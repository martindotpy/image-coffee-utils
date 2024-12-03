package xyz.cupscoffee.imagecoffeeutils.shared.adapter.out.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Indicates that annotated class is a <em>WebRestControllerAdapter</em>.
 *
 * <p>
 * Represents a web rest controller adapter component. This annotation serves as a
 * specialization of Spring's {@link @RestController}, allowing for
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
@RestController
public @interface WebRestControllerAdapter {
    @AliasFor(annotation = RestController.class)
    String value() default "";
}
