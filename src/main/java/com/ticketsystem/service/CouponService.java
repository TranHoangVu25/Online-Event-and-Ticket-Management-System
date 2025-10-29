package com.ticketsystem.service;

import com.ticketsystem.dto.request.CouponCreateRequest;
import com.ticketsystem.dto.request.CouponUpdateRequest;
import com.ticketsystem.entity.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {
    Coupon createCoupon(CouponCreateRequest request);

    Coupon updateCoupon(int coupon_id, CouponUpdateRequest request);

    void deleteCoupon(int coupon_id);

    List<Coupon> getAllCoupon();
}
