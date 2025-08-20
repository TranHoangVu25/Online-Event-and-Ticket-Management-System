package com.ticketsystem.service;

import com.ticketsystem.dto.request.LocationRequest;
import com.ticketsystem.dto.response.LocationResponse;
import com.ticketsystem.entity.Location;
import com.ticketsystem.mapper.LocationMapper;
import com.ticketsystem.repository.LocationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    LocationRepository locationRepository;
    LocationMapper locationMapper;

    public Location createLocation(LocationRequest request){
        Location location = locationMapper.toLocation(request);
        return locationRepository.save(location);
    }
    public LocationResponse updateLocation(LocationRequest request,int id){
        Location location = locationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Id not found"));
        locationMapper.updateLocation(location,request);
        return locationMapper.toLocationResponse(locationRepository.save(location));
    }
}
