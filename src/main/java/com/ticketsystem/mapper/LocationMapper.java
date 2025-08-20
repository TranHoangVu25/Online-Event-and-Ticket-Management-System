package com.ticketsystem.mapper;


import com.ticketsystem.dto.request.LocationRequest;
import com.ticketsystem.dto.response.LocationResponse;
import com.ticketsystem.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationRequest request);
    LocationResponse toLocationResponse(Location location);
    void updateLocation(@MappingTarget Location location, LocationRequest request);
}
