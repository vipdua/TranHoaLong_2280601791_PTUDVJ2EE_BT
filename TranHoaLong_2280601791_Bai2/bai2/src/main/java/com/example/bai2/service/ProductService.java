package com.example.bai2.service;

import com.example.bai2.model.Product;
import com.example.bai2.repository.ProductRepository;
import com.example.bai2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả sản phẩm (không phân trang)
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

    // Tìm kiếm sản phẩm (không phân trang)
    public List<Product> getSearchProducts(String key) {
        return productRepository.findByNameContainingIgnoreCase(key);
    }

    // Lấy tất cả sản phẩm có phân trang và sắp xếp (KHÔNG lọc)
    public Page<Product> getProductsByPage(int page, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    // Lấy tất cả sản phẩm có phân trang, sắp xếp và lọc theo category
    public Page<Product> getProductsByPage(int page, int pageSize, String sortField, String sortDir, Integer categoryId) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageable);
        }
        return productRepository.findAll(pageable);
    }

    // Tìm kiếm sản phẩm có phân trang, sắp xếp và lọc theo category
    public Page<Product> getSearchProducts(String key, int page, int pageSize, String sortField, String sortDir, Integer categoryId) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        boolean hasKey = key != null && !key.trim().isEmpty();
        boolean hasCategory = categoryId != null;

        if (hasKey && hasCategory) {
            // Cả tìm kiếm và lọc category
            return productRepository.findByNameContainingIgnoreCaseAndCategoryId(key, categoryId, pageable);
        } else if (hasKey) {
            // Chỉ tìm kiếm
            return productRepository.findByNameContainingIgnoreCase(key, pageable);
        } else if (hasCategory) {
            // Chỉ lọc category
            return productRepository.findByCategoryId(categoryId, pageable);
        } else {
            // Không có điều kiện
            return productRepository.findAll(pageable);
        }
    }

    public List<Product> getProductsByCategory(Integer categoryId, Long currentProductId, int limit) {
        return productRepository.findByCategoryIdAndIdNot(categoryId, currentProductId, PageRequest.of(0, limit))
                .getContent();
    }
}