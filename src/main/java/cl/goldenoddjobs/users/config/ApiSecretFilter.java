package cl.goldenoddjobs.users.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Only API Gateway knows the shared API key: it injects it as the x-api-secret header.
 * Every request without the right value is rejected before authentication, so the service
 * cannot be used by calling the instance directly.
 */
public class ApiSecretFilter extends OncePerRequestFilter {

    public static final String HEADER = "x-api-secret";

    private final byte[] secret;

    public ApiSecretFilter(String secret) {
        this.secret = secret == null ? new byte[0] : secret.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) || hasValidSecret(request)) {
            chain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"invalid or missing x-api-secret\"}");
    }

    private boolean hasValidSecret(HttpServletRequest request) {
        String received = request.getHeader(HEADER);
        if (received == null || secret.length == 0) {
            return false;
        }
        return MessageDigest.isEqual(secret, received.getBytes(StandardCharsets.UTF_8));
    }
}
