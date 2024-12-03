package xyz.cupscoffee.imagecoffeeutils.shared.domain.validation;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * Validator class.
 *
 * @see Validation
 */
@RequiredArgsConstructor
public final class Validator {
    public static List<ValidationError> validate(Validation... validations) {
        return List.of(validations).stream()
                .map(Validation::validate)
                .flatMap(List::stream)
                .toList();
    }
}
