package com.example.bai2.service;

import com.example.bai2.model.Product;
import com.example.bai2.repository.ProductRepository;
import com.example.bai2.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lưu hoặc cập nhật sản phẩm
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Lấy sản phẩm theo id
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}