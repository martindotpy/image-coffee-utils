package xyz.cupscoffee.imagecoffeeutils.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Logging global filter for logging requests to the gateway.
 */
@Slf4j
@Order(-1)
@Component
public class LoggingGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethod().name();
        String realIp = request.getHeaders().getFirst("X-Real-IP");
        String path = request.getURI().getPath();

        if (path.equals("/favicon.ico"))
            return chain.filter(exchange);

        StringBuilder sb = new StringBuilder();
        sb.append(
                String.format(
                        "\u001B[35m%s\u001B[0m to \u001B[35m%s\u001B[0m",
                        method,
                        path));

        if (realIp != null) {
            sb.append(String.format(" from \u001B[35m%s\u001B[0m", realIp));
        }

        log.info(sb.toString());

        return chain.filter(exchange);
    }
}
