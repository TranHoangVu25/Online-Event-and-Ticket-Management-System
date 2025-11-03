package com.ticketsystem.dto.request;

import com.ticketsystem.entity.Coupon;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {
    Map<Integer, Integer> tickets;
    String method;
    String couponCode;
}
