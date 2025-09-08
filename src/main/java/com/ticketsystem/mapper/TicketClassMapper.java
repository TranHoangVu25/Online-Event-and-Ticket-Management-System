package com.ticketsystem.mapper;

import com.ticketsystem.dto.request.TicketClassCreationRequest;
import com.ticketsystem.dto.request.TicketClassUpdateRequest;
import com.ticketsystem.dto.response.TicketClassResponse;
import com.ticketsystem.entity.TicketClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

//khi thêm unmappedTargetPolicy = ReportingPolicy.IGNORE thì sẽ map đc cả các trường @Id
public interface TicketClassMapper {

    @Mapping(target = "event",ignore = true)
    TicketClass toTicketClass(TicketClassCreationRequest request);

    TicketClassResponse toTicketClassResponse(TicketClass ticketClass);

    @Mapping(target = "event",ignore = true)
    void updateTicketClass(@MappingTarget TicketClass ticketClass, TicketClassUpdateRequest request);
}
