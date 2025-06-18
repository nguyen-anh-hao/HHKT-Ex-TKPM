package org.example.backend.common;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CachingResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream cachedBody;
    private final ServletOutputStream servletOutputStream;
    private final PrintWriter printWriter;
    private boolean outputStreamCalled = false;
    private boolean writerCalled = false;

    public CachingResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.cachedBody = new ByteArrayOutputStream();
        this.servletOutputStream = new CachedServletOutputStream(cachedBody);
        this.printWriter = new PrintWriter(new OutputStreamWriter(cachedBody, StandardCharsets.UTF_8));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writerCalled) {
            throw new IllegalStateException("getWriter() has already been called for this response");
        }
        outputStreamCalled = true;
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStreamCalled) {
            throw new IllegalStateException("getOutputStream() has already been called for this response");
        }
        writerCalled = true;
        return printWriter;
    }

    public byte[] getCachedBody() {
        if (writerCalled) {
            printWriter.flush();
        }
        return cachedBody.toByteArray();
    }

    public String getCachedBodyAsString() {
        return new String(getCachedBody(), StandardCharsets.UTF_8);
    }

    public void copyBodyToResponse() throws IOException {
        if (cachedBody.size() > 0) {
            super.getResponse().getOutputStream().write(cachedBody.toByteArray());
        }
    }

    private static class CachedServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream buffer;

        public CachedServletOutputStream(ByteArrayOutputStream buffer) {
            this.buffer = buffer;
        }

        @Override
        public void write(int b) throws IOException {
            buffer.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}