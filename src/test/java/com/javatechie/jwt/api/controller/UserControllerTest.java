package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.dto.UserResponse;
import com.javatechie.jwt.api.exception.NotFoundException;
import com.javatechie.jwt.api.filter.JwtFilter;
import com.javatechie.jwt.api.service.CustomUserDetailsService;
import com.javatechie.jwt.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.javatechie.jwt.api.repository.UserRepository;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getUserReturnsUserWhenFound() throws Exception {
        given(userService.getUserById(1)).willReturn(new UserResponse(1, "john", "john@example.com"));

        mockMvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("john"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserReturnsNotFoundWhenMissing() throws Exception {
        given(userService.getUserById(99)).willThrow(new NotFoundException("User with id 99 was not found"));

        mockMvc.perform(get("/api/users/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("User with id 99 was not found"))
                .andExpect(jsonPath("$.path").value("/api/users/99"));
    }
}
