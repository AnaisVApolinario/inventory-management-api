package com.avadev.inventory.service.impl;

import com.avadev.inventory.dto.request.ProductRequest;
import com.avadev.inventory.dto.response.ProductResponse;
import com.avadev.inventory.entity.Product;
import com.avadev.inventory.exception.ResourceNotFoundException;
import com.avadev.inventory.mapper.ProductMapper;
import com.avadev.inventory.repository.ProductRepository;
import com.avadev.inventory.service.ProductService;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper){
        this.repository= repository;
        this.mapper = mapper;
    }
    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = mapper.toEntity(request);
        Product savedProduct = repository.save(product);
        return mapper.toResponse(savedProduct);
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }


    private Product findProductOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    }
    @Override
    public ProductResponse findById(Long id) {
        Product product = findProductOrThrow(id);
            return mapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findProductOrThrow(id);

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSalePrice(request.getSalePrice());
        product.setStock(request.getStock());
        product.setMinimumStock(request.getMinimumStock());
        product.setActive(request.getActive());

        Product updated = repository.save(product);
        return mapper.toResponse(updated);
    }

    @Override
    public List<ProductResponse> searchByName(String name){

        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Override
    public void delete(Long id) {
        Product product = findProductOrThrow(id);
        repository.delete(product);
    }
}
