package org.example.backend.common;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CachingRequestWrapper extends HttpServletRequestWrapper {
    private final String cachedBody;

    public CachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedBody = readBody(request);
    }

    public CachingRequestWrapper(HttpServletRequest request, String body) {
        super(request);
        this.cachedBody = body;
    }

    private String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                cachedBody.getBytes(StandardCharsets.UTF_8));
        return new CachedServletInputStream(byteArrayInputStream);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public String getCachedBody() {
        return cachedBody;
    }

    private static class CachedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream buffer;

        public CachedServletInputStream(ByteArrayInputStream buffer) {
            this.buffer = buffer;
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}