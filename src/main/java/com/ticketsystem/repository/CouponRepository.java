package com.ticketsystem.repository;

import com.ticketsystem.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    Optional<Coupon> findByCode(String code);
}
