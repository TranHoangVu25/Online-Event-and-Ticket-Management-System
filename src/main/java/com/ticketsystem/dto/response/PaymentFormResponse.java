package com.ticketsystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentFormResponse {
    EventResponse event;
    List<TicketClassResponse> ticketClass;
    BigDecimal totalPrice;
    List<Integer> ticketRemain;
    Integer soldQuantity;
}
