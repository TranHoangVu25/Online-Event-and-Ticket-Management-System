package com.ticketsystem.controller.user;

import com.ticketsystem.dto.response.*;
import com.ticketsystem.service.EventService;
import com.ticketsystem.service.TicketClassService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserEventController {
    EventService eventService;
    TicketClassService ticketClassService;

    @GetMapping("/main-event")
    public String getEvents(Model model) {
        List<EventFormResponse> eventForms = new ArrayList<>();
        List<EventResponse> events = eventService.getEvents();
        for(EventResponse event : events){
            List<TicketClassResponse> ticketClass = ticketClassService.getTicketClasses(event.getId());
            List<Integer> remainTickets = ticketClassService.calculateRemainTicket(ticketClass);
            EventFormResponse dto = new EventFormResponse(event,ticketClass,remainTickets);
            eventForms.add(dto);
        }
//        EventFormResponse eventForm = new EventFormResponse(events,ticketClass,remainTickets);
        model.addAttribute("eventForms", eventForms);
        return "customer/main-event";
    }

    @GetMapping("/event-details/{id}")
    public String getEventDetail(@PathVariable int id, Model model) {
        EventResponse event = eventService.getEvent(id);
        List<TicketClassResponse> ticketClassResponses = ticketClassService.getTicketClasses(id);
        EventFormDetail eventForm = new EventFormDetail(event,ticketClassResponses);
        model.addAttribute("eventForm", eventForm);
        return "customer/event-details";
    }

    @GetMapping("/buy-ticket/{id}")
    public String showBuyTicket(@PathVariable int id, Model model) {
        EventResponse event = eventService.getEvent(id);
        List<TicketClassResponse> ticketClassResponses = ticketClassService.getTicketClasses(id);
        BigDecimal totalPrice = ticketClassService.totalPrice1(ticketClassResponses.get(0));
        List<Integer> remainTicket = ticketClassService.calculateRemainTicket(ticketClassResponses);
        EventFormBuyTicket eventForm = new EventFormBuyTicket(event,ticketClassResponses,totalPrice,remainTicket);
        model.addAttribute("eventForm", eventForm);
//        model.addAttribute("event", event);
        return "customer/buy-ticket";
    }
}
