package com.ticketsystem.dto.response;

import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.TicketClass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormUserOrderResponse {
    private TicketClass ticketClass;
    private Order order;
    private OrderDetailResponse orderDetail;
}
