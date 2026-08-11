package com.avadev.inventory.controller;

import com.avadev.inventory.dto.request.ProductRequest;
import com.avadev.inventory.dto.response.ProductResponse;
import com.avadev.inventory.exception.ResourceNotFoundException;
import com.avadev.inventory.service.ProductService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void shouldGetProductById() throws Exception {

        // Arrange
        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setSku("LAP-001");
        response.setName("Laptop Lenovo");
        response.setSalePrice(new BigDecimal("3200"));
        response.setStock(10);
        response.setActive(true);

        when(service.findById(1L))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sku").value("LAP-001"))
                .andExpect(jsonPath("$.name").value("Laptop Lenovo"))
                .andExpect(jsonPath("$.salePrice").value(3200))
                .andExpect(jsonPath("$.stock").value(10));

        verify(service).findById(1L);
    }
    @Test
    void shouldCreateProduct() throws Exception{
        // Arrange
        ProductRequest request = new ProductRequest();
        request.setSku("LAP-001");
        request.setName("Laptop Lenovo");
        request.setDescription("ThinkPad T14");
        request.setPurchasePrice(new BigDecimal("2500"));
        request.setSalePrice(new BigDecimal("3200"));
        request.setStock(10);
        request.setMinimumStock(2);
        request.setActive(true);

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setSku("LAP-001");
        response.setName("Laptop Lenovo");
        response.setSalePrice(new BigDecimal("3200"));
        response.setStock(10);
        response.setActive(true);

        when(service.create(any(ProductRequest.class)))
                .thenReturn(response);
        // Act + Assert
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sku").value("LAP-001"))
                .andExpect(jsonPath("$.name").value("Laptop Lenovo"));

        verify(service).create(any(ProductRequest.class));

    }

    @Test
    void shouldReturnBadRequestWhenProductIsInvalid() throws Exception {

        ProductRequest request = new ProductRequest();

        request.setSku("");
        request.setName("");
        request.setPurchasePrice(new BigDecimal("-100"));
        request.setSalePrice(new BigDecimal("-50"));
        request.setStock(-1);
        request.setMinimumStock(-1);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isBadRequest());

        verify(service, never())
                .create(any(ProductRequest.class));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {

        // Arrange
        Long productId = 999L;

        when(service.findById(productId))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        // Act + Assert
        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product not found"))
                .andExpect(jsonPath("$.path").value("/api/v1/products/999"));

        verify(service).findById(productId);
    }

    @Test
    void shouldUpdateProductSuccessfully() throws Exception {

        // Arrange
        Long productId = 1L;

        ProductRequest request = new ProductRequest();
        request.setSku("LAP-001");
        request.setName("Laptop Lenovo Updated");
        request.setDescription("Updated description");
        request.setPurchasePrice(new BigDecimal("2600"));
        request.setSalePrice(new BigDecimal("3500"));
        request.setStock(20);
        request.setMinimumStock(5);
        request.setActive(true);

        ProductResponse response = new ProductResponse();
        response.setId(productId);
        response.setSku("LAP-001");
        response.setName("Laptop Lenovo Updated");
        response.setSalePrice(new BigDecimal("3500"));
        response.setStock(20);
        response.setActive(true);

        when(service.update(eq(productId), any(ProductRequest.class)))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sku").value("LAP-001"))
                .andExpect(jsonPath("$.name").value("Laptop Lenovo Updated"))
                .andExpect(jsonPath("$.salePrice").value(3500))
                .andExpect(jsonPath("$.stock").value(20));

        verify(service)
                .update(eq(productId), any(ProductRequest.class));
    }

    @Test
    void shouldDeleteProductSuccessfully() throws Exception {

        // Arrange
        Long productId = 1L;

        // Act + Assert
        mockMvc.perform(delete("/api/v1/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(service).delete(productId);
    }


}
