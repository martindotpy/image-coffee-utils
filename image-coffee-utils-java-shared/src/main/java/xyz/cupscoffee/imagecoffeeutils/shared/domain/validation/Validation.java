package xyz.cupscoffee.imagecoffeeutils.shared.domain.validation;

import java.util.List;

/**
 * Validation interface.
 */
public interface Validation {
    List<ValidationError> validate();
}
