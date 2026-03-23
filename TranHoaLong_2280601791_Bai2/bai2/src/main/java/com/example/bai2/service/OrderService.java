package com.example.bai2.service;

import com.example.bai2.model.*;
import com.example.bai2.repository.OrderDetailRepository;
import com.example.bai2.repository.OrderRepository;
import com.example.bai2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    // Tạo đơn hàng từ giỏ hàng
    @Transactional
    public Order createOrder(String customerName, String customerEmail,
                             String customerPhone, String shippingAddress) {

        // Lấy giỏ hàng hiện tại
        List<CartItem> cartItems = cartService.getItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setShippingAddress(shippingAddress);

        double totalAmount = 0;

        // Tạo các OrderDetail từ CartItem
        for (CartItem cartItem : cartItems) {
            // Lấy thông tin sản phẩm từ database
            Product product = productRepository.findById(Long.valueOf(cartItem.getId()))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + cartItem.getId()));

            // Tạo OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(Double.valueOf(product.getPrice()));

            // Thêm vào order
            order.addOrderDetail(orderDetail);

            // Tính tổng tiền
            totalAmount += orderDetail.getSubtotal();
        }

        // Set tổng tiền cho order
        order.setTotalAmount(totalAmount);

        // Lưu order (cascade sẽ tự lưu order details)
        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartService.clear();

        return savedOrder;
    }

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Cập nhật trạng thái đơn hàng
    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    // Lấy chi tiết đơn hàng
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}