package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.service.LoginRateLimiter;
import com.javatechie.jwt.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginRateLimiter loginRateLimiter;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to javatechie !!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest, HttpServletRequest request) throws Exception {
        String rateLimitKey = buildRateLimitKey(authRequest, request);
        loginRateLimiter.checkRateLimit(rateLimitKey);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
            loginRateLimiter.reset(rateLimitKey);
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    private String buildRateLimitKey(AuthRequest authRequest, HttpServletRequest request) {
        String username = authRequest.getUserName() == null
                ? "anonymous"
                : authRequest.getUserName().toLowerCase(Locale.ENGLISH);
        String clientIp = extractClientIp(request);
        return clientIp + ":" + username;
    }

    private String extractClientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String forwardedHeader = request.getHeader("X-Forwarded-For");
        if (forwardedHeader != null && !forwardedHeader.trim().isEmpty()) {
            return forwardedHeader.split(",")[0].trim();
        }
        String remoteAddr = request.getRemoteAddr();
        return remoteAddr != null ? remoteAddr : "unknown";
    }
}
