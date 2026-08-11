package com.avadev.inventory.service.impl;

import com.avadev.inventory.dto.request.ProductRequest;
import com.avadev.inventory.dto.response.ProductResponse;
import com.avadev.inventory.entity.Product;
import com.avadev.inventory.exception.ResourceNotFoundException;
import com.avadev.inventory.mapper.ProductMapper;
import com.avadev.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void shouldCreateProductSuccessfully(){
        // Arrange
        ProductRequest request = new ProductRequest();
        request.setSku("LAP-001");
        request.setName("Laptop Lenovo");
        request.setPurchasePrice(new BigDecimal("2500"));
        request.setSalePrice(new BigDecimal("3200"));
        request.setStock(10);
        request.setMinimumStock(2);
        request.setActive(true);

        Product product = new Product();
        product.setSku("LAP-001");
        product.setName("Laptop Lenovo");
        product.setSalePrice(new BigDecimal("3200"));

        Product savedProduct = new Product();
        savedProduct.setSku("LAP-001");
        savedProduct.setName("Laptop Lenovo");
        savedProduct.setSalePrice(new BigDecimal("3200"));

        ProductResponse expectedResponse = new ProductResponse();
        expectedResponse.setSku("LAP-001");
        expectedResponse.setName("Laptop Lenovo");
        expectedResponse.setSalePrice(new BigDecimal("3200"));

        when(mapper.toEntity(request)).thenReturn(product);
        when(repository.save(product)).thenReturn(savedProduct);
        when(mapper.toResponse(savedProduct)).thenReturn(expectedResponse);

        // ACT
        ProductResponse result = service.create(request);

        // ASSERT
        assertNotNull(result);
        assertEquals("LAP-001", result.getSku());
        assertEquals("Laptop Lenovo", result.getName());
        assertEquals(new BigDecimal("3200"), result.getSalePrice());

        verify(repository).save(product);
    }

     @Test
    void shouldFindProductByIdSuccessfully(){
        //Arrange
        Long productId = 1L;

         Product product = new Product();
         product.setSku("LAP-001");
         product.setName("Laptop Lenovo");

         ProductResponse expectedResponse = new ProductResponse();
         expectedResponse.setSku("LAP-001");
         expectedResponse.setName("Laptop Lenovo");

         when(repository.findById(productId))
                 .thenReturn(Optional.of(product));

         when(mapper.toResponse(product))
                 .thenReturn(expectedResponse);

         ProductResponse result = service.findById(productId);

         assertNotNull(result);
         assertEquals("LAP-001", result.getSku());
         assertEquals("Laptop Lenovo", result.getName());

         verify(repository).findById(productId);
     }
    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        // Arrange
        Long productId = 999L;

        when(repository.findById(productId))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(productId)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(repository).findById(productId);
    }

    @Test
    void shouldFindAllProductsSuccessfully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Product product1 = new Product();
        product1.setSku("LAP-001");
        product1.setName("Laptop Lenovo");

        Product product2 = new Product();
        product2.setSku("MOU-001");
        product2.setName("Mouse Logitech");

        ProductResponse response1 = new ProductResponse();
        response1.setSku("LAP-001");
        response1.setName("Laptop Lenovo");

        ProductResponse response2 = new ProductResponse();
        response2.setSku("MOU-001");
        response2.setName("Mouse Logitech");

        Page<Product> productPage =
                new PageImpl<>(List.of(product1, product2));

        when(repository.findAll(pageable))
                .thenReturn(productPage);

        when(mapper.toResponse(product1))
                .thenReturn(response1);

        when(mapper.toResponse(product2))
                .thenReturn(response2);
        // Act
        Page<ProductResponse> result = service.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("LAP-001", result.getContent().get(0).getSku());
        assertEquals("MOU-001", result.getContent().get(1).getSku());

        verify(repository).findAll(pageable);
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        // Arrange
        Long productId = 1L;

        ProductRequest request = new ProductRequest();
        request.setSku("LAP-001");
        request.setName("Laptop Lenovo Updated");
        request.setDescription("Updated description");
        request.setPurchasePrice(new BigDecimal("2600"));
        request.setSalePrice(new BigDecimal("3400"));
        request.setStock(20);
        request.setMinimumStock(5);
        request.setActive(true);

        Product existingProduct = new Product();
        existingProduct.setSku("LAP-001");
        existingProduct.setName("Laptop Lenovo");

        ProductResponse expectedResponse = new ProductResponse();
        expectedResponse.setSku("LAP-001");
        expectedResponse.setName("Laptop Lenovo Updated");

        when(repository.findById(productId))
                .thenReturn(Optional.of(existingProduct));

        when(repository.save(existingProduct))
                .thenReturn(existingProduct);

        when(mapper.toResponse(existingProduct))
                .thenReturn(expectedResponse);

        // Act
        ProductResponse result = service.update(productId, request);

        // Assert
        assertNotNull(result);
        assertEquals("Laptop Lenovo Updated", result.getName());

        // También comprobamos la entidad
        assertEquals("Laptop Lenovo Updated", existingProduct.getName());
        assertEquals(new BigDecimal("3400"), existingProduct.getSalePrice());
        assertEquals(20, existingProduct.getStock());

        verify(repository).findById(productId);
        verify(repository).save(existingProduct);
    }
    @Test
    void shouldDeleteProductSuccessfully() {

        // Arrange
        Long productId = 1L;

        Product product = new Product();
        product.setSku("LAP-001");
        product.setName("Laptop Lenovo");

        when(repository.findById(productId))
                .thenReturn(Optional.of(product));

        // Act
        service.delete(productId);

        // Assert
        verify(repository).findById(productId);
        verify(repository).delete(product);
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {

        // Arrange
        Long productId = 999L;

        when(repository.findById(productId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(productId)
        );

        verify(repository).findById(productId);

        verify(repository, never()).delete(any(Product.class));
    }




}
