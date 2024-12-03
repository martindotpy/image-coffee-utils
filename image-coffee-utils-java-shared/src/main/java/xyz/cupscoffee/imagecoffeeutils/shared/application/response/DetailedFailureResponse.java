package xyz.cupscoffee.imagecoffeeutils.shared.application.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a detailed failure response.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DetailedFailureResponse {
    private String message;
    private List<String> details;
}
