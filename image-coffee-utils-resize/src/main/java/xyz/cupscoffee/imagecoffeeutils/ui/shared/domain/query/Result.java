package xyz.cupscoffee.imagecoffeeutils.ui.shared.domain.query;

/**
 * Result of a query or command.
 *
 * <p>
 * It can be only a successful result or a failure result.
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
     * @param <S>     the type of the successful response
     * @param <F>     the type of the failure response
     * @param success the successful response
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> success(S success) {
        return new Result<>(success, null);
    }

    /**
     * Create a empty successful result.
     *
     * @param <S>     the type of the successful response
     * @param <F>     the type of the failure response
     * @param failure the failure response
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> emptySuccess() {
        return new Result<>(null, null);
    }

    /**
     * Create a failure result.
     *
     * @param <S>     the type of the successful response
     * @param <F>     the type of the failure response
     * @param failure the failure response
     * @return the result
     */
    public static <S, F extends Failure> Result<S, F> failure(F failure) {
        return new Result<>(null, failure);
    }

    public S getSuccess() {
        return success;
    }

    public F getFailure() {
        return failure;
    }

    public boolean isSuccess() {
        return success != null;
    }

    public boolean isFailure() {
        return failure != null;
    }
}
