package com.example.bai2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bai2.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository đã có sẵn CRUD:
    // save()
    // findById()
    // findAll()
    // deleteById()
    Page<Product> findByCategoryIdAndIdNot(Integer categoryId, Long productId, Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String name);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);


    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(String name, Integer categoryId, Pageable pageable);
}