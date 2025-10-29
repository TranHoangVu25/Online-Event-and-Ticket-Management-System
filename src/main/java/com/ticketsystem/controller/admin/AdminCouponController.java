package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.request.CouponCreateRequest;
import com.ticketsystem.dto.request.CouponUpdateRequest;
import com.ticketsystem.entity.Coupon;
import com.ticketsystem.service.CouponService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin-coupons")
public class AdminCouponController {
    CouponService couponService;

    @GetMapping()
    public String getAllCoupon(
            Model model
    ){
        List<Coupon> coupons = couponService.getAllCoupon();
         model.addAttribute("coupon",coupons);
         return "/admin/admin-coupon";
    }
    @PostMapping()
    public String createCoupon(
            @Valid @RequestAttribute("coupon") CouponCreateRequest coupon
    ){
        couponService.createCoupon(coupon);
        return "/redirect:/admin-coupon";
    }
    @PutMapping("/{coupon_id}")
    public String updateCoupon(
            @PathVariable int coupon_id,
            @ModelAttribute("coupon") @Valid CouponUpdateRequest request
            ){
        couponService.updateCoupon(coupon_id,request);
        return "/redirect:/admin-coupon";
    }
}
