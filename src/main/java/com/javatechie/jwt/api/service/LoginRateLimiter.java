package com.javatechie.jwt.api.service;

import com.javatechie.jwt.api.exception.RateLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginRateLimiter {

    private final Map<String, AttemptWindow> attempts = new ConcurrentHashMap<>();
    private final int maxAttempts;
    private final long windowMillis;
    private final long windowSeconds;

    public LoginRateLimiter(
            @Value("${security.login.rate-limit.max-attempts:5}") int maxAttempts,
            @Value("${security.login.rate-limit.window-seconds:60}") long windowSeconds) {
        this.maxAttempts = maxAttempts;
        this.windowSeconds = windowSeconds;
        this.windowMillis = TimeUnit.SECONDS.toMillis(windowSeconds);
    }

    public void checkRateLimit(String key) {
        long now = System.currentTimeMillis();
        AttemptWindow attemptWindow = attempts.computeIfAbsent(key, k -> new AttemptWindow(now));
        synchronized (attemptWindow) {
            if (now - attemptWindow.windowStart >= windowMillis) {
                attemptWindow.windowStart = now;
                attemptWindow.count = 0;
            }
            if (attemptWindow.count >= maxAttempts) {
                throw new RateLimitExceededException(maxAttempts, windowSeconds);
            }
            attemptWindow.count++;
        }
    }

    public void reset(String key) {
        attempts.remove(key);
    }

    private static final class AttemptWindow {
        private long windowStart;
        private int count;

        private AttemptWindow(long windowStart) {
            this.windowStart = windowStart;
            this.count = 0;
        }
    }
}
