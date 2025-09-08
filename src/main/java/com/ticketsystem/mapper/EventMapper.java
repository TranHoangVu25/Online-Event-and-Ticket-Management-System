package com.ticketsystem.mapper;

import com.ticketsystem.dto.request.EventCreationRequest;
import com.ticketsystem.dto.request.EventUpdateRequest;
import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    @Mapping(target = "location", ignore = true)
    Event toEvent(EventCreationRequest request);

    EventResponse toEventResponse(Event event);

    @Mapping(target = "location", ignore = true)
    void updateEvent(@MappingTarget Event event, EventUpdateRequest request);
}