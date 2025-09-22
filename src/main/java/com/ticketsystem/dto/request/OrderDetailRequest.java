package com.ticketsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.OrderDetailId;
import com.ticketsystem.entity.TicketClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {

    private OrderDetailId id;
//    private Order order;
//    private TicketClass ticketClass;
    private int quantity;
    private BigDecimal price;

}
