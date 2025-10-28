package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.dto.PagedResponse;
import com.javatechie.jwt.api.dto.ProductResponse;
import com.javatechie.jwt.api.filter.JwtFilter;
import com.javatechie.jwt.api.repository.UserRepository;
import com.javatechie.jwt.api.service.CustomUserDetailsService;
import com.javatechie.jwt.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void getProductsReturnsPagedContent() throws Exception {
        List<ProductResponse> content = List.of(
                new ProductResponse(1L, "Book", "Interesting book", new BigDecimal("9.99")),
                new ProductResponse(2L, "Pen", "Blue ink pen", new BigDecimal("1.99"))
        );
        PagedResponse<ProductResponse> page = new PagedResponse<>(0, 20, 2, 1, content);
        given(productService.getProducts(0, 20)).willReturn(page);

        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Book"));
    }

    @Test
    void getProductsReturnsEmptyForOutOfRange() throws Exception {
        PagedResponse<ProductResponse> page = new PagedResponse<>(3, 20, 0, 0, Collections.emptyList());
        given(productService.getProducts(anyInt(), anyInt())).willReturn(page);

        mockMvc.perform(get("/api/products?page=3&size=20").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.totalElements").value(0));
    }
}
