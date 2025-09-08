package com.ticketsystem.dto.request;

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
public class TicketClassCreationRequest {
    Event event;
    String name;
    BigDecimal price;
    int totalQuantity;
    int soldQuantity;
}
