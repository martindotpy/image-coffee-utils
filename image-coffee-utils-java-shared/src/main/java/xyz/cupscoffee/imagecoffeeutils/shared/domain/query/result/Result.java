package xyz.cupscoffee.imagecoffeeutils.shared.domain.query.result;

import java.util.Objects;

import xyz.cupscoffee.imagecoffeeutils.shared.domain.query.failure.Failure;

/**
 * The response of the query.
 *
 * <p>
 * It can be only a successful result or a failure.
 * </p>
 */
public final class Result<S, F extends Failure> {
    private final S success;
    private final F failure;

    private Result(S success, F failure) {
        this.success = success;
        this.failure = failure;
    }

    /**
     * Create a successful result.
     *
     * @param <S>     the type of the successful response.
     * @param <E>     the type of the failure response.
     * @param success the successful response.
     * @return the result
     */
    public static <S, E extends Failure> Result<S, E> success(S success) {
        Objects.requireNonNull(success, "The success response must not be null");
        return new Result<>(success, null);
    }

    /**
     * Create an empty successful result.
     *
     * @param <S> the type of the successful response.
     * @param <E> the type of the failure response.
     * @return the result
     */
    public static <S, E extends Failure> Result<S, E> success() {
        return new Result<>(null, null);
    }

    /**
     * Create a failure result.
     *
     * @param <S>     the type of the successful response.
     * @param <F>     the type of the failure response.
     * @param failure the failure response.
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> failure(F failure) {
        Objects.requireNonNull(failure, "The failure response must not be null");
        return new Result<>(null, failure);
    }

    public S getSuccess() {
        return success;
    }

    public F getFailure() {
        return failure;
    }

    public boolean isSuccess() {
        return success != null || failure == null;
    }

    public boolean isFailure() {
        return failure != null;
    }
}
