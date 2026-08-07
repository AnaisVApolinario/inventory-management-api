package com.avadev.inventory.mapper;

import com.avadev.inventory.dto.request.ProductRequest;
import com.avadev.inventory.dto.response.ProductResponse;
import com.avadev.inventory.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequest request){

        Product product = new Product();

        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSalePrice(request.getSalePrice());
        product.setStock(request.getStock());
        product.setMinimumStock(request.getMinimumStock());
        product.setActive(request.getActive());

        return product;
    }

    public ProductResponse toResponse(Product product){

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setSku(product.getSku());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setSalePrice(product.getSalePrice());
        response.setStock(product.getStock());
        response.setActive(product.getActive());

        return response;
    }

}
