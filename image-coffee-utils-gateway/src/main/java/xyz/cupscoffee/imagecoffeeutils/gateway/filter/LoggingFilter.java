package xyz.cupscoffee.imagecoffeeutils.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Logging filter for logging requests to the gateway.
 */
@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String realIP = request.getHeaders().getFirst("X-Real-IP");
            String path = request.getURI().getPath();

            if (realIP != null &&
                    !(path.equals("/") ||
                            path.equals("/favicon.ico") ||
                            path.startsWith("/assets")))
                log.info(String.format(
                        "\u001B[35m%s\u001B[0m to \u001B[35m%s\u001B[0m from \u001B[35m%s\u001B[0m IP",
                        request.getMethod(),
                        path,
                        realIP));

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Empty config
    }
}
