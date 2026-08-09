package com.avadev.inventory.service;

import com.avadev.inventory.dto.request.ProductRequest;
import com.avadev.inventory.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);

    Page<ProductResponse> findAll(Pageable pageable);

    ProductResponse findById(Long id);

    ProductResponse update(Long id, ProductRequest request);
    List<ProductResponse> searchByName(String name);

    void delete(Long id);
}
