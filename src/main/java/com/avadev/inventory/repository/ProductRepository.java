package com.avadev.inventory.repository;

import com.avadev.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long > {

}
