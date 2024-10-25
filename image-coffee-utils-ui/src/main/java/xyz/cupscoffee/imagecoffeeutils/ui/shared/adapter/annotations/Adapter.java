package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * Indicates that annotated class is a <em>Adapter</em> component.
 *
 * <p>
 * This annotation serves as a specialization of Spring's
 * {@link @Component}, allowing for implementation classes to be
 * auto-detected through classpath scanning by Spring.
 * </p>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Adapter {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
