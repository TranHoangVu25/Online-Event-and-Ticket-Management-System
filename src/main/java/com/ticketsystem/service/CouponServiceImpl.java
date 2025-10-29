package com.ticketsystem.service;

import com.ticketsystem.dto.request.CouponCreateRequest;
import com.ticketsystem.dto.request.CouponUpdateRequest;
import com.ticketsystem.entity.Coupon;
import com.ticketsystem.exception.Error;
import com.ticketsystem.repository.CouponRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CouponServiceImpl implements CouponService{
    CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(CouponCreateRequest coupon) {
        Coupon c = new Coupon().builder()
                .condition(coupon.getCondition())
                .expire(coupon.getExpire())
                .code(coupon.getCode())
                .discount(coupon.getDiscount())
                .type(coupon.getType())
                .build();
        return couponRepository.save(c);
    }

    @Override
    public Coupon updateCoupon(int coupon_id, CouponUpdateRequest request) {
        Coupon c = couponRepository.findById(coupon_id)
                .orElseThrow(()-> new RuntimeException(Error.COUPON_NOT_EXISTED.name()));
        c.setCondition(request.getCondition());
        c.setExpire(request.getExpire());
        c.setCode(request.getCode());
        c.setDiscount(request.getDiscount());
        c.setType(request.getType());

        return couponRepository.save(c);
    }

    @Override
    public void deleteCoupon(int coupon_id) {
        Coupon c = couponRepository.findById(coupon_id)
                .orElseThrow(()-> new RuntimeException(Error.COUPON_NOT_EXISTED.name()));
        couponRepository.deleteById(c.getCoupon_id());
    }

    @Override
    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon getCouponById(int coupon_id) {
        return couponRepository.findById(coupon_id)
                .orElseThrow(()->new RuntimeException("Coupon not found"));
    }
}
