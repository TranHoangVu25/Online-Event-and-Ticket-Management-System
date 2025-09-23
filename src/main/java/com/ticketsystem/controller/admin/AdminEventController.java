package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.request.*;
import com.ticketsystem.dto.response.EventFormUpdateResponse;
import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.dto.response.TicketClassResponse;
import com.ticketsystem.entity.TicketClass;
import com.ticketsystem.entity.User;
import com.ticketsystem.repository.EventRepository;
import com.ticketsystem.repository.UserRepository;
import com.ticketsystem.service.EventService;
import com.ticketsystem.service.TicketClassService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")

public class AdminEventController {

    EventService eventService;
    TicketClassService ticketClassService;
    EventRepository eventRepository;
    UserRepository userRepository;

    //hiển thị danh sách events
    @GetMapping("/admin-events")
    public String getEvents(@RequestParam(value = "keyword",required = false) String keyword
            ,Model model) {
        List<EventResponse> events;
        List<EventViewRequest> eventViews = new ArrayList<>();
        if(keyword!=null && !keyword.trim().isEmpty()){
            events = eventService.findEventByName(keyword);
        }
//        else {
            events = eventService.getEvents();


        for (EventResponse eventResponse:events){
            TicketClass ticketClass = ticketClassService.getTicketClassByEventId(eventResponse.getId());
            BigDecimal revenue = ticketClassService.totalPrice(ticketClass);
            eventViews.add(new EventViewRequest(eventResponse,ticketClass,revenue));
        }
        model.addAttribute("events", events);
        model.addAttribute("keyword",keyword);
        model.addAttribute("eventViews", eventViews);

        return "admin/admin-events";

    }

    //chuyển qua trang create event
    @GetMapping("/admin-create-event")
    public String showCreateForm(Model model){
        model.addAttribute("eventForm",new EventFormCreationRequest());
        return "admin/admin-create-event";
    }

    @PostMapping("/admin-create-event")
    public String createEvent(
                @ModelAttribute("eventForm")@Valid EventFormCreationRequest request,
                HttpSession session
    )
            throws Exception {
        EventCreationRequest eventCreationRequest = request.getEvent();

        TicketClassCreationRequest ticketClassCreationRequest = request.getTicketClass();

        eventService.createEvent(eventCreationRequest,session);
        ticketClassService.createTicketClass(ticketClassCreationRequest,eventCreationRequest);
        return "redirect:/admin/admin-events";
    }

    @GetMapping("/admin-update-event/{id}")
    public String showUpdateForm(@PathVariable int id,Model model){
        EventResponse eventResponse = eventService.getEvent(id);
        List<TicketClassResponse> ticketClass = ticketClassService.getTicketClasses(id);

        EventFormUpdateResponse eventForm = new EventFormUpdateResponse();
        eventForm.setEvent(eventResponse);
        eventForm.setTicketClass(ticketClass);

        model.addAttribute("eventForm",eventForm);

        return "admin/admin-update-event";
    }

    @PostMapping("/admin-update-event/{id}")
    String updateEvent(@PathVariable int id, @ModelAttribute("eventForm")
    @Valid EventFormUpdateRequest request){

        EventUpdateRequest eventUpdateRequest;
        List<TicketClassUpdateRequest> ticketClassUpdateRequest;

        eventUpdateRequest = request.getEvent();
        ticketClassUpdateRequest = request.getTicketClass();

        eventService.updateEvent(eventUpdateRequest,id);
        ticketClassService.updateTicketClass(ticketClassUpdateRequest);
        return "redirect:/admin/admin-events";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return "OK";
    }

}
