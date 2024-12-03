package xyz.cupscoffee.imagecoffeeutils.resize.shared.adapter.out.handler;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler controller.
 *
 * <p>
 * Handles global exceptions thrown by the application.
 * </p>
 */
@ControllerAdvice
@RestController
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandlerController {
    /**
     * Handles exceptions that extend from {@link ServletException}.
     *
     * @param e The exception thrown by the application
     * @return A redirection to the error page (temporary solution)
     */
    @ExceptionHandler(ServletException.class)
    public String handleServletException(ServletException e) {
        log.error("Servlet exception: " + e.getMessage());

        return "redirect:/error";
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException} exceptions.
     *
     * @param e The exception thrown by the application
     * @return A redirection to the error page (temporary solution)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Http request method not supported: " + e.getMessage());

        return "redirect:/error";
    }

    /**
     * Handles {@link UnsupportedOperationException} exceptions.
     *
     * @param e The exception thrown by the application
     * @return A redirection to the error page (temporary solution)
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public String handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("Unsupported operation: ", e);

        return "redirect:/error";
    }
}