package com.javatechie.jwt.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.jwt.api.dto.OrderRequest;
import com.javatechie.jwt.api.dto.OrderResponse;
import com.javatechie.jwt.api.filter.JwtFilter;
import com.javatechie.jwt.api.repository.UserRepository;
import com.javatechie.jwt.api.service.CustomUserDetailsService;
import com.javatechie.jwt.api.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void createOrderReturnsCreated() throws Exception {
        OrderResponse response = new OrderResponse(5L, 1, "SKU-100", 2, new BigDecimal("19.99"));
        given(orderService.createOrder(any(OrderRequest.class))).willReturn(response);

        OrderRequest request = new OrderRequest();
        request.setUserId(1);
        request.setProductCode("SKU-100");
        request.setQuantity(2);
        request.setTotalAmount(new BigDecimal("19.99"));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/orders/5"))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.productCode").value("SKU-100"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.totalAmount").value(19.99));
    }

    @Test
    void createOrderReturnsBadRequestForInvalidPayload() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setUserId(null);
        request.setProductCode("");
        request.setQuantity(0);
        request.setTotalAmount(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.length()").value(4));
    }
}
