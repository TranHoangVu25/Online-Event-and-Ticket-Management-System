package com.ticketsystem.service;

import com.ticketsystem.dto.request.EventCreationRequest;
import com.ticketsystem.dto.request.TicketClassCreationRequest;
import com.ticketsystem.dto.request.TicketClassUpdateRequest;
import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.dto.response.TicketClassResponse;
import com.ticketsystem.entity.Event;
import com.ticketsystem.entity.TicketClass;
import com.ticketsystem.mapper.TicketClassMapper;
import com.ticketsystem.repository.EventRepository;
import com.ticketsystem.repository.TicketClassRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class TicketClassService {
    TicketClassMapper ticketClassMapper;
    TicketClassRepository ticketClassRepository;
    EventRepository eventRepository;

    public TicketClass createTicketClass(TicketClassCreationRequest request, EventCreationRequest eventCreationRequest){
        Event event = eventRepository.findByName(eventCreationRequest.getName())
                .orElseThrow(()->new RuntimeException("Event Name is not found"));

        TicketClass ticketClass = ticketClassMapper.toTicketClass(request);
        ticketClass.setEvent(event);
        return ticketClassRepository.save(ticketClass);
    }

    public List<TicketClassResponse> getTicketClasses(int eventId){
        Event event = new Event();
        event.setId(eventId);
        return ticketClassRepository
                .findAllByEvent(event)
                .stream()
                .map(ticketClassMapper::toTicketClassResponse)
                .toList();
    }

    public void deleteTicketClass(int id){
        ticketClassRepository.deleteById(id);
    }

    public List<TicketClassResponse> updateTicketClass(List<TicketClassUpdateRequest> requests){
        List<TicketClassResponse> ticketClassResponses = new ArrayList<>();
        for (TicketClassUpdateRequest request:requests){
            log.info(String.valueOf(request.getId()));
            log.info(request.getName());
            var ticketClass = ticketClassRepository.findById(request.getId())
                    .orElseThrow(()->new RuntimeException("Id not found"));
            ticketClassMapper.updateTicketClass(ticketClass,request);
            ticketClassResponses.add(ticketClassMapper.
                    toTicketClassResponse(ticketClassRepository.save(ticketClass)));
        }


        return ticketClassResponses;
    }

    public BigDecimal totalPrice(TicketClass ticketClass){
        BigDecimal revenue = BigDecimal.ZERO;;

        BigDecimal price = ticketClass.getPrice();
        BigDecimal soldQuantity = BigDecimal.valueOf(ticketClass.getSoldQuantity());

        revenue = revenue.add(price.multiply(soldQuantity));

        return revenue;
    }
    public BigDecimal totalPrice1(TicketClassResponse ticketClass){
        BigDecimal revenue = BigDecimal.ZERO;;

        BigDecimal price = ticketClass.getPrice();
        BigDecimal soldQuantity = BigDecimal.valueOf(ticketClass.getSoldQuantity());

        revenue = revenue.add(price.multiply(soldQuantity));

        return revenue;
    }

//    public Integer getSoldQuantity(EventResponse event){
//        return ticketClassRepository.findTotalSoldByEvent(event.getId());
//    }
    public TicketClass getTicketClass(int id){
        TicketClass response = ticketClassRepository.findWithEvent(id);
        return response;
    }

    public List<Integer> calculateRemainTicket(List<TicketClassResponse> responses){
        List<Integer> remainTickets = new ArrayList<>();
        for (TicketClassResponse response:responses){
           int remainTicket = response.getTotalQuantity()-response.getSoldQuantity();
            remainTickets.add(remainTicket);
        }
        return remainTickets;
    }
}
