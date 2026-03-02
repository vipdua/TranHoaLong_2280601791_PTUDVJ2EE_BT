package com.example.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bai2.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // CRUD đã có sẵn từ JpaRepository
}