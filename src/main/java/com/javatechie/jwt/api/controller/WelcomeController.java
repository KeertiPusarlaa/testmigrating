package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.entity.User;
import com.javatechie.jwt.api.repository.UserRepository;
import com.javatechie.jwt.api.util.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<User> users() {
        return userRepository.findAll();
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
}
