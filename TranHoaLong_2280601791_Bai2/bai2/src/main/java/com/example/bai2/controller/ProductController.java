package com.example.bai2.controller;

import com.example.bai2.model.Category;
import com.example.bai2.model.Product;
import com.example.bai2.service.CategoryService;
import com.example.bai2.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Danh sách sản phẩm
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    // Hiển thị form tạo sản phẩm
    @GetMapping("/create")
    public String create(Model model) {
        Product product = new Product();
        product.setCategory(new Category());

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    // Xử lý tạo sản phẩm
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("product") Product newProduct,
            BindingResult result,
            @RequestParam("category.id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        // Upload ảnh
        productService.updateImage(newProduct, imageProduct);

        // Gán category
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        // Lưu sản phẩm
        productService.add(newProduct);

        return "redirect:/products";
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product found = productService.get(id);

        if (found == null) {
            return "error/404";
        }

        model.addAttribute("product", found);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute("product") Product editProduct,
            BindingResult result,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Nếu có upload ảnh mới
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        productService.update(editProduct);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}