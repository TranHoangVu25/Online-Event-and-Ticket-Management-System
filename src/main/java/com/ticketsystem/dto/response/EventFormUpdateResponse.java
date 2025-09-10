package com.ticketsystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFormUpdateResponse {
    EventResponse event;
    List<TicketClassResponse> ticketClass;
}
