package com.ticketsystem.service;

import com.ticketsystem.dto.request.EventCreationRequest;
import com.ticketsystem.dto.request.EventUpdateRequest;
import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.entity.Event;
import com.ticketsystem.entity.Location;
import com.ticketsystem.entity.User;
import com.ticketsystem.mapper.EventMapper;
import com.ticketsystem.repository.EventRepository;
import com.ticketsystem.repository.LocationRepository;
import com.ticketsystem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class EventService {
    EventRepository eventRepository;
    EventMapper eventMapper;
    LocationRepository locationRepository;
    UserRepository userRepository;

    public Event createEvent(EventCreationRequest request, HttpSession session) throws Exception {
        if (eventRepository.existsByName(request.getName())){
            throw new Exception("Event name is existed");
        }
        Location location = locationRepository.save(request.getLocation());
        Event event = eventMapper.toEvent(request);
        event.setLocation(location);
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("id not found"));
        event.setCreator(user);

        return eventRepository.save(event);
    }

    public List<EventResponse> getEvents(){
        return eventRepository
                .findAll()
                .stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    public EventResponse getEvent(int id){
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventResponse eventResponse = eventMapper.toEventResponse(event);
        return eventResponse;
    }
    public void deleteEvent(int id){
        eventRepository.deleteById(id);
    }

    public EventResponse updateEvent(EventUpdateRequest request, int id){
        Event event = eventRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id not found"));
        eventMapper.updateEvent(event,request);

        return eventMapper.toEventResponse(eventRepository.save(event));
    }

    public List<EventResponse> findEventByName(String name){
        return eventRepository
                .findByName(name)
                .stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    public Event getEventEntityById(Integer id) {
        return eventRepository
                .findById(id).
                orElseThrow(() -> new RuntimeException("Not found!"));
    }

}
