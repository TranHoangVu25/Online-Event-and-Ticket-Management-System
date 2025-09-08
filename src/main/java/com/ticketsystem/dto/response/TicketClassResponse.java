package com.ticketsystem.dto.response;

import com.ticketsystem.entity.Event;
import com.ticketsystem.validator.EndDateConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EndDateConstraint
public class TicketClassResponse {
    Event event;
    Integer id;
    String name;
    BigDecimal price;
    int totalQuantity;
    int soldQuantity;
}
