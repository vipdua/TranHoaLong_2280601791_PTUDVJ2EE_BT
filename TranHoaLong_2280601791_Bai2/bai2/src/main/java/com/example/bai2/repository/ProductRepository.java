package com.example.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bai2.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository đã có sẵn CRUD:
    // save()
    // findById()
    // findAll()
    // deleteById()
}