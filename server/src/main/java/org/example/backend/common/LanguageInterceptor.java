package org.example.backend.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LanguageInterceptor implements HandlerInterceptor {
    public static final ThreadLocal<String> CURRENT_LANGUAGE = new ThreadLocal<>();
    public static final String DEFAULT_LANGUAGE = "vi";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String language = request.getHeader("Accept-Language");
        if (language == null || language.isEmpty()) {
            language = DEFAULT_LANGUAGE;
        }

        if (!language.equals("en") && !language.equals("vi")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        CURRENT_LANGUAGE.set(language);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CURRENT_LANGUAGE.remove();
    }
}
