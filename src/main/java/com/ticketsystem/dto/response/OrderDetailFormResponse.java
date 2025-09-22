package com.ticketsystem.dto.response;

import com.ticketsystem.entity.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailFormResponse {
    Order order;
    TicketClassResponse ticket;
}
