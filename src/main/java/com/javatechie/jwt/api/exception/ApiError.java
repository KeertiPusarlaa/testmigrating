package com.javatechie.jwt.api.exception;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class ApiError {
    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final List<ApiFieldError> fieldErrors;

    private ApiError(Builder builder) {
        this.timestamp = builder.timestamp;
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.path = builder.path;
        this.fieldErrors = builder.fieldErrors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<ApiFieldError> getFieldErrors() {
        return fieldErrors == null ? Collections.emptyList() : fieldErrors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private List<ApiFieldError> fieldErrors;

        private Builder() {
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder fieldErrors(List<ApiFieldError> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }

        public ApiError build() {
            return new ApiError(this);
        }
    }
}
