package com.ticketsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, precision = 18, scale = 0)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String method;

    /**
     * Trạng thái thanh toán:
     * 0: Đang chờ (Pending)
     * 1: Thành công (Success)
     * 2: Thất bại (Failed)
     */
    @Column(nullable = false)
    private int status;

    @Column(name = "transaction_code", length = 100)
    private String transactionCode;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}