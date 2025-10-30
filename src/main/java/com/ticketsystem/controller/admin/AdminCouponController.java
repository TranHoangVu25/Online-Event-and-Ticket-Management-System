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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin-coupon")
public class AdminCouponController {
    CouponService couponService;

    @GetMapping()
    public String getAllCoupon(
            Model model
    ) {
        List<Coupon> coupons = couponService.getAllCoupon();
        model.addAttribute("coupons", coupons);
        model.addAttribute("newCoupon", new CouponCreateRequest());
        model.addAttribute("couponUpdate", new CouponUpdateRequest());
        return "admin/admin-coupons";
    }

    @PostMapping("/admin-create-coupon")
    public String createCoupon(
            @ModelAttribute("newCoupon") @Valid CouponCreateRequest coupon,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            List<Coupon> coupons = couponService.getAllCoupon();
            model.addAttribute("coupons", coupons);
            model.addAttribute("couponUpdate", new CouponUpdateRequest());
            return "admin/admin-coupons";
        }
        couponService.createCoupon(coupon);
        return "redirect:/admin-coupon";
    }

    @GetMapping("/{coupon_id}")
    @ResponseBody
    public Coupon getCouponById(
            @PathVariable int coupon_id
    ) {
        return couponService.getCouponById(coupon_id);
    }

    @PostMapping("/admin-update-coupon/{coupon_id}")
    public String updateCoupon(
            @PathVariable int coupon_id,
            @ModelAttribute("couponUpdate") @Valid CouponUpdateRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            List<Coupon> coupons = couponService.getAllCoupon();
            model.addAttribute("coupons", coupons);
            model.addAttribute("newCoupon", new CouponCreateRequest());
            model.addAttribute("failedUpdateId", coupon_id);
            return "admin/admin-coupons";
        }
        couponService.updateCoupon(coupon_id, request);
        return "redirect:/admin-coupon";
    }

    @DeleteMapping("/remove-coupon/{coupon_id}")
    @ResponseBody
    public String deleteCoupon(@PathVariable int coupon_id) {
        couponService.deleteCoupon(coupon_id);
        return "OK";
    }
}
