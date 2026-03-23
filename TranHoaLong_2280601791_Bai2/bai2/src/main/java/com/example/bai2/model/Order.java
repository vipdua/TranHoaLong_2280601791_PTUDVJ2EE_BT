package com.example.bai2.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED

    // Thông tin khách hàng (có thể thêm sau)
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;

    // Mối quan hệ với OrderDetail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Phương thức helper để thêm order detail
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
}