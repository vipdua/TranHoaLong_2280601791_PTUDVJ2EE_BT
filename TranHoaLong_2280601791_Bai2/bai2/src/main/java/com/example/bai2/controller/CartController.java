package com.example.bai2.controller;

import com.example.bai2.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Hiển thị giỏ hàng
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/list";
    }

    // Thêm sản phẩm vào giỏ - SỬA: int -> Long
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {
        cartService.addToCart(id);
        return "redirect:/cart";
    }

    // Cập nhật số lượng - SỬA: int -> Long
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // Xóa sản phẩm khỏi giỏ - SỬA: int -> Long
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    // Xóa toàn bộ giỏ hàng
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
}