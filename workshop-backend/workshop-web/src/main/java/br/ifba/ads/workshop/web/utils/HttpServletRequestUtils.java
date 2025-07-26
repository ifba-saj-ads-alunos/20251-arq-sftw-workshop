package br.ifba.ads.workshop.web.utils;

import jakarta.servlet.http.HttpServletRequest;

public final class HttpServletRequestUtils {

    public static String extractToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : "";
    }

    public static String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
