package com.example.bai2.controller;

import com.example.bai2.model.Product;
import com.example.bai2.service.CategoryService;
import com.example.bai2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách sản phẩm có phân trang, sắp xếp và lọc
    @GetMapping
    public String listProducts(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer categoryId) {

        Page<Product> productPage = productService.getProductsByPage(page, size, sortField, sortDir, categoryId);

        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("currentUrl", "/products");
        model.addAttribute("categories", categoryService.getAllCategories()); // Lấy danh sách category
        model.addAttribute("selectedCategoryId", categoryId); // Giữ lại category đã chọn

        return "product/products";
    }

    // Tìm kiếm sản phẩm có phân trang, sắp xếp và lọc
    @GetMapping("/search")
    public String searchProducts(
            Model model,
            @RequestParam(required = false) String key,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer categoryId) {

        Page<Product> productPage = productService.getSearchProducts(key, page, size, sortField, sortDir, categoryId);

        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("key", key);
        model.addAttribute("currentUrl", "/products/search");
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategoryId", categoryId);

        return "product/products";
    }

    // Các method khác giữ nguyên...
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/create";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);

        // Gợi ý sản phẩm cùng danh mục
        List<Product> relatedProducts = productService.getProductsByCategory(
                product.getCategory().getId(),
                id,
                4
        );
        model.addAttribute("relatedProducts", relatedProducts);

        return "product/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute("product") Product product) {
        // Lấy sản phẩm cũ để giữ lại các thông tin không thay đổi
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            // Cập nhật các trường mới
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImage(product.getImage());
            existingProduct.setCategory(product.getCategory());

            // Lưu lại
            productService.saveProduct(existingProduct);
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")  // ← Đổi thành POST
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}