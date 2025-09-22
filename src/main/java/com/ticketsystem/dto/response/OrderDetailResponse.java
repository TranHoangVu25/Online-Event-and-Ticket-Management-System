package com.ticketsystem.dto.response;

import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.OrderDetailId;
import com.ticketsystem.entity.TicketClass;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {

    private OrderDetailId id;
//    private Order order;
//    private TicketClass ticketClass;
    private int quantity;
    private BigDecimal price;

}
