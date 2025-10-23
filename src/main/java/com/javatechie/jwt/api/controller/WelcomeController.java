package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.repository.UserRepository;
import com.javatechie.jwt.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/users")
    public List<UserSummary> users() {
        return userRepository.findAll().stream()
                .map(user -> new UserSummary(user.getId(), user.getUserName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }

    public static class UserSummary {
        private final int id;
        private final String userName;
        private final String email;

        public UserSummary(int id, String userName, String email) {
            this.id = id;
            this.userName = userName;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }
    }
}
