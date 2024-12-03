package xyz.cupscoffee.imagecoffeeutils.shared.domain.validation;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SimpleValidation class.
 *
 * @see Validation
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public final class SimpleValidation implements Validation {
    private final boolean evaluatedExpression;
    private final String field;
    private final String message;

    @Override
    public List<ValidationError> validate() {
        if (!evaluatedExpression) {
            log.debug("Validation passed for field `{}`", field);

            return List.of();
        }

        log.warn("Validation failed for field `{}` with message `{}`", field, message);

        return List.of(new ValidationError(field, message));
    }
}
