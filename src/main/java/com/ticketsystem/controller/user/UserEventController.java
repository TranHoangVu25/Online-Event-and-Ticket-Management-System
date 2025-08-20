package com.ticketsystem.controller.user;

import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.service.EventService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserEventController {
    EventService eventService;

    @GetMapping("/main-event")
    public String getEvents(Model model) {
        List<EventResponse> events = eventService.getEvents();
        model.addAttribute("events", events);
        return "customer/main-event";
    }

    @GetMapping("/event-details/{id}")
    public String getEventDetail(@PathVariable int id, Model model) {
        EventResponse event = eventService.getEvent(id);
        model.addAttribute("event", event);
        return "customer/event-details";
    }

    @GetMapping("/buy-ticket/{id}")
    public String showBuyTicket(@PathVariable int id, Model model) {
        EventResponse event = eventService.getEvent(id);

        model.addAttribute("event", event);
        return "customer/buy-ticket";
    }
}
