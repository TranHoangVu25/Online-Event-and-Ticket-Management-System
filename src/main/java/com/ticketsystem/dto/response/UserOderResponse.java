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
public class UserOderResponse {
    Integer orderId;
    LocalDateTime orderDate;
    String eventName;
    String thumbnailUrl;
    LocalDateTime eventStartTime;
    String ticketClassName;
    Integer ticketClassId;
    int quantity;
    BigDecimal totalAmount;
}
