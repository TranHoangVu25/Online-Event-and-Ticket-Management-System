package com.ticketsystem.dto.request;

import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.dto.response.TicketClassResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFormUpdateRequest {
    EventUpdateRequest event;
    List<TicketClassUpdateRequest> ticketClass;
}
