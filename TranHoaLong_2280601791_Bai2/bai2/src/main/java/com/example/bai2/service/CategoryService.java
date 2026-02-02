package com.example.bai2.service;

import com.example.bai2.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final List<Category> categories = new ArrayList<>();

    // Khởi tạo dữ liệu mẫu
    public CategoryService() {
        categories.add(new Category(1, "Sách giáo khoa"));
        categories.add(new Category(2, "Tiểu thuyết"));
        categories.add(new Category(3, "Khoa học"));
        categories.add(new Category(4, "Thiếu nhi"));
        categories.add(new Category(5, "Văn phòng phẩm"));
        categories.add(new Category(6, "Thiết bị học tập"));
        categories.add(new Category(7, "Đồ điện tử"));
        categories.add(new Category(8, "Đồ gia dụng"));
        categories.add(new Category(9, "Thời trang"));
        categories.add(new Category(10, "Đồ chơi"));
        categories.add(new Category(11, "Thể thao"));
        categories.add(new Category(12, "Âm nhạc"));
        categories.add(new Category(13, "Điện thoại"));
    }

    // Lấy tất cả danh mục
    public List<Category> getAll() {
        return categories;
    }

    // Lấy danh mục theo id
    public Category get(int id) {
        return categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}