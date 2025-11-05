package com.javatechie.jwt.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException(int maxAttempts, long windowSeconds) {
        super(String.format("Too many login attempts. Allowed %d attempts in %d seconds. Please try again later.",
                maxAttempts, windowSeconds));
    }
}
