package com.example.bai2.repository;

import com.example.bai2.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm đơn hàng theo email khách hàng
    List<Order> findByCustomerEmail(String email);

    // Phân trang đơn hàng
    Page<Order> findAll(Pageable pageable);

    // Đếm số đơn hàng theo trạng thái
    long countByStatus(String status);
}