package com.ticketsystem.validator;

import com.ticketsystem.dto.request.CouponCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.util.Objects;

public class CouponExpireValidator implements ConstraintValidator<CouponExpireConstraint, CouponCreateRequest> {
    @Override
    public void initialize(CouponExpireConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CouponCreateRequest request, ConstraintValidatorContext context) { // Đổi tên tham số
        if (Objects.isNull(request) || request.getExpire() == null) {
            return true; // Bỏ qua nếu không có ngày
        }

        // Nếu ngày hợp lệ
        if (request.getExpire().isAfter(LocalDateTime.now())) {
            return true;
        }

        // --- BẮT ĐẦU SỬA LỖI ---
        // Nếu ngày không hợp lệ (đã qua)
        // 1. Tắt thông báo lỗi global mặc định
        context.disableDefaultConstraintViolation();

        // 2. Xây dựng một lỗi mới và gán nó cho trường "expire"
        context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate() // Lấy message từ @CouponExpireConstraint
                )
               // .addPropertyNode("expire") // <-- Gán lỗi cho trường "expire"
                .addConstraintViolation();
        // --- KẾT THÚC SỬA LỖI ---

        return false; // Trả về false vì không hợp lệ
    }
}