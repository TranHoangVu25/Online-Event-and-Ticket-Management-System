package com.ticketsystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormOrderDetailResponse {
    Integer orderId;
    LocalDateTime orderDate;
    String eventName;
    String thumbnailUrl;
    LocalDateTime eventStartTime;
    String eventLocation;
    String ticketClassName;
    int quantity;
    BigDecimal totalAmount;
    String fullName;
    String email;
    String phoneNumber;
    int status;
}
