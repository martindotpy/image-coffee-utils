package xyz.cupscoffee.imagecoffeeutils.shared.domain.validation;

/**
 * Represents a external payload validator provider.
 */
public final class ExternalPayloadValidatorProvider {
    private static ExternalPayloadValidator externalValidation;

    public static void set(ExternalPayloadValidator externalValidation) {
        ExternalPayloadValidatorProvider.externalValidation = externalValidation;
    }

    public static ExternalPayloadValidator get() {
        return externalValidation;
    }
}
