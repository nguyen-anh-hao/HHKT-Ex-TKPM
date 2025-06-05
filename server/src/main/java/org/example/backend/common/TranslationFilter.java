package org.example.backend.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class TranslationFilter implements Filter {

    public static final ThreadLocal<String> CURRENT_LANGUAGE = new ThreadLocal<>();
    public static final String DEFAULT_LANGUAGE = "vi";

    @Autowired
    private TranslationService translationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String language = httpRequest.getHeader("Accept-Language");
            if (!StringUtils.hasText(language)) {
                language = DEFAULT_LANGUAGE;
            }

            if (!language.equals("en") && !language.equals("vi")) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            CURRENT_LANGUAGE.set(language);

            CachingRequestWrapper wrappedRequest = new CachingRequestWrapper(httpRequest);
            CachingResponseWrapper wrappedResponse = new CachingResponseWrapper(httpResponse);

            if (isJsonRequest(httpRequest) && shouldTranslateRequest(language)) {
                String requestBody = wrappedRequest.getCachedBody();
                if (StringUtils.hasText(requestBody)) {
                    try {
                        JsonNode requestJson = objectMapper.readTree(requestBody);
                        JsonNode translatedJson = translationService.translateJsonNode(
                                requestJson, language, DEFAULT_LANGUAGE);

                        String translatedBody = objectMapper.writeValueAsString(translatedJson);
                        wrappedRequest = createRequestWithNewBody(httpRequest, translatedBody);
                        log.info("Request translated successfully to " + language);
                    } catch (Exception e) {
                        log.error("Request translation failed: " + e.getMessage(), e);
                    }
                }
            }

            chain.doFilter(wrappedRequest, wrappedResponse);

            if (isJsonResponse(wrappedResponse) && shouldTranslateResponse(language)) {
                String responseBody = wrappedResponse.getCachedBodyAsString();
                if (StringUtils.hasText(responseBody)) {
                    try {
                        JsonNode responseJson = objectMapper.readTree(responseBody);
                        JsonNode translatedJson = translationService.translateJsonNode(
                                responseJson, DEFAULT_LANGUAGE, language);

                        String translatedResponse = objectMapper.writeValueAsString(translatedJson);

                        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        httpResponse.setContentLength(translatedResponse.getBytes().length);
                        httpResponse.getOutputStream().write(translatedResponse.getBytes());
                        log.info("Response translated successfully to " + language);
                        return;
                    } catch (Exception e) {
                        log.error("Response translation failed: " + e.getMessage(), e);
                    }
                }
            }

            wrappedResponse.copyBodyToResponse();

        } finally {
            CURRENT_LANGUAGE.remove();
        }
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    private boolean isJsonResponse(CachingResponseWrapper response) {
        String contentType = response.getContentType();
        return contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }

    private boolean shouldTranslateRequest(String language) {
        return !DEFAULT_LANGUAGE.equals(language);
    }

    private boolean shouldTranslateResponse(String language) {
        return !DEFAULT_LANGUAGE.equals(language);
    }

    private CachingRequestWrapper createRequestWithNewBody(HttpServletRequest originalRequest, String newBody)
            throws IOException {
        return new CachingRequestWrapper(originalRequest, newBody);
    }
}