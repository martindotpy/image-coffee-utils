package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that annotated class is a <em>PersistenceAdapter</em>.
 *
 * <p>
 * Represents a Persistence adapter component. This annotation serves as a
 * specialization of Spring's {@link @Adapter}, allowing for
 * implementation classes to be auto-detected through classpath scanning by
 * Spring.
 * </p>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Adapter
public @interface PersistenceAdapter {
    @AliasFor(annotation = Adapter.class)
    String value() default "";
}
