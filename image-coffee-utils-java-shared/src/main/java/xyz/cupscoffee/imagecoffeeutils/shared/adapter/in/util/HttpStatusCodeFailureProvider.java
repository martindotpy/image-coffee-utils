package xyz.cupscoffee.imagecoffeeutils.shared.adapter.in.util;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.context.ApplicationContext;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.cupscoffee.imagecoffeeutils.shared.domain.query.failure.Failure;

/**
 * Provider for HTTP status codes for failures.
 */
@Slf4j
@RequiredArgsConstructor
public final class HttpStatusCodeFailureProvider {
    private static final Map<Class<? extends Failure>, HttpStatusCodeFailureHandler<? extends Failure>> httpStatusCodeFailureHandlers = new HashMap<>();

    private final ApplicationContext applicationContext;

    @PostConstruct
    public void initializeHandlers() {
        @SuppressWarnings("rawtypes")
        Map<String, HttpStatusCodeFailureHandler> handlers = applicationContext
                .getBeansOfType(HttpStatusCodeFailureHandler.class);

        for (HttpStatusCodeFailureHandler<?> handler : handlers.values()) {
            Class<? extends Failure> failureClass = getFailureType(handler);

            if (failureClass != null) {
                addHttpStatusCodeFailureHandler(failureClass, handler);
            }
        }
    }

    /**
     * Gets the failure type from the handler class.
     *
     * @param handlerClass The handler class.
     * @return The failure type.
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Failure> getFailureType(HttpStatusCodeFailureHandler<?> handlerClass) {
        return (Class<? extends Failure>) ((ParameterizedType) handlerClass.getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

    /**
     * Adds a HTTP status code failure handler.
     *
     * @param failureClass The failure class.
     * @param handler      The handler.
     */
    private static void addHttpStatusCodeFailureHandler(
            Class<? extends Failure> failureClass,
            HttpStatusCodeFailureHandler<?> handler) {
        log.info("Adding HTTP status code failure handler for failure: {}", failureClass.getName());

        httpStatusCodeFailureHandlers.put(failureClass, handler);
    }

    /**
     * Gets the HTTP status code for a failure.
     *
     * @param failure The failure.
     * @param <T>     The failure type.
     * @return The HTTP status code.
     */
    public static <T extends Failure> int get(T failure) {
        @SuppressWarnings("unchecked")
        HttpStatusCodeFailureHandler<T> handler = (HttpStatusCodeFailureHandler<T>) httpStatusCodeFailureHandlers
                .get(failure.getClass());

        Objects.requireNonNull(handler, "No handler found for failure: " + failure.getClass().getName());

        return handler.getHttpStatusCode(failure);
    }
}
