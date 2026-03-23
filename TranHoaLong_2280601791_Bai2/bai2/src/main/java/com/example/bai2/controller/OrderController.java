package com.example.bai2.controller;

import com.example.bai2.model.Order;
import com.example.bai2.service.CartService;
import com.example.bai2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    // Hiển thị form checkout
    @GetMapping("/checkout")
    public String showCheckoutForm(Model model) {
        // Kiểm tra giỏ hàng có trống không
        if (cartService.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        model.addAttribute("order", new Order());
        return "order/checkout";
    }

    // Xử lý đặt hàng
    @PostMapping("/checkout")
    public String processCheckout(
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String customerPhone,
            @RequestParam String shippingAddress,
            Model model) {

        try {
            Order order = orderService.createOrder(customerName, customerEmail,
                    customerPhone, shippingAddress);
            model.addAttribute("order", order);
            return "order/success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "order/checkout";
        }
    }

    // Xem danh sách đơn hàng
    @GetMapping("/list")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/list";
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/order/list";
        }

        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderService.getOrderDetails(id));
        return "order/detail";
    }

    // Cập nhật trạng thái đơn hàng
    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/order/detail/" + id;
    }
}