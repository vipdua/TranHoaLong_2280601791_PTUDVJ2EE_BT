package com.example.bai2.repository;

import com.example.bai2.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Tìm chi tiết đơn hàng theo order
    List<OrderDetail> findByOrderId(Long orderId);

    // Tìm chi tiết đơn hàng theo product
    List<OrderDetail> findByProductId(Long productId);
}