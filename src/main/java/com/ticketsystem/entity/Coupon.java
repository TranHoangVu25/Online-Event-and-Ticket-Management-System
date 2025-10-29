package com.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "coupon")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer coupon_id;
    @Column(name = "`condition`")
    Integer condition;
    @Column(nullable = false)
    LocalDateTime expire;
    @Column(unique = true)
    String code;
    @Column(nullable = false)
    BigDecimal discount;

    //loại coupon
    //1 :fixed (tiền mặt)
    //2 :percent (phần trăm)
    @Column(nullable = false,name = "`type`")
    Integer type;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    List<Order> orderList;

    @ManyToMany(mappedBy = "coupons")
    @ToString.Exclude
    @JsonManagedReference
    List<User> users;
}
