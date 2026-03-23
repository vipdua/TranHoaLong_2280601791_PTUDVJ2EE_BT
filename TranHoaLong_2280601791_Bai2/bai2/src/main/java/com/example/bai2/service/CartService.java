package com.example.bai2.service;

import com.example.bai2.model.CartItem;
import com.example.bai2.model.Product;
import com.example.bai2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {

    private List<CartItem> items = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getItems() {
        return items;
    }

    // Thêm vào giỏ hàng - SỬA: int -> Long
    public void addToCart(Long productId) {
        // Tìm sản phẩm từ database
        Product findProduct = productRepository.findById(productId).orElse(null);
        if (findProduct == null) {
            return; // Không tìm thấy sản phẩm
        }

        // Kiểm tra sản phẩm đã có trong giỏ chưa
        items.stream()
                .filter(item -> item.getId().equals(findProduct.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setId(Math.toIntExact(productId)); // Chuyển Long sang Integer
                            newItem.setName(findProduct.getName());
                            newItem.setImage(findProduct.getImage());
                            newItem.setPrice(findProduct.getPrice());
                            newItem.setQuantity(1);
                            items.add(newItem);
                        }
                );
    }

    // Cập nhật số lượng quantity - SỬA: int -> Long
    public void updateQuantity(Long productId, int quantity) {
        // Tìm sản phẩm trong giỏ hàng để cập nhật số lượng
        items.stream()
                .filter(item -> item.getId().equals(Math.toIntExact(productId)))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    // Xóa sản phẩm khỏi giỏ hàng - SỬA: int -> Long
    public void removeFromCart(Long productId) {
        items.removeIf(item -> item.getId().equals(Math.toIntExact(productId)));
    }

    // Xóa toàn bộ giỏ hàng
    public void clear() {
        items.clear();
    }

    // Tính tổng tiền
    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}