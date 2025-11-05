package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.entity.User;
import com.javatechie.jwt.api.repository.UserRepository;
import com.javatechie.jwt.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to javatechie !!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        authenticate(authRequest.getUserName(), authRequest.getPassword());
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) {
        authenticate(authRequest.getUserName(), authRequest.getPassword());

        String token = jwtUtil.generateToken(authRequest.getUserName());
        User user = userRepository.findByUserName(authRequest.getUserName());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User details not found");
        }

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("id", user.getId());
        userPayload.put("userName", user.getUserName());
        userPayload.put("email", user.getEmail());
        response.put("token", token);
        response.put("user", userPayload);
        return ResponseEntity.ok(response);
    }

    private void authenticate(String userName, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, password)
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }
}
