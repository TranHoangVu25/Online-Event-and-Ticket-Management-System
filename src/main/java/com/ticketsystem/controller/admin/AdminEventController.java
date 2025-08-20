package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.request.EventCreationRequest;
import com.ticketsystem.dto.request.EventUpdateRequest;
import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.service.EventService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")

public class AdminEventController {

    EventService eventService;

    //hiển thị danh sách events
    @GetMapping("/admin-events")
    public String getEvents(@RequestParam(value = "keyword",required = false) String keyword ,Model model) {
        List<EventResponse> events;
        if(keyword!=null && !keyword.trim().isEmpty()){
            events = eventService.findEventByName(keyword);
        }
        else {
            events = eventService.getEvents();
        }
        model.addAttribute("events", events);
        model.addAttribute("keyword",keyword);
        return "admin/admin-events";
    }

    //chuyển qua trang create event
    @GetMapping("/admin-create-event")
    public String showCreateForm(Model model){
        model.addAttribute("event",new EventCreationRequest());
        return "admin/admin-create-event";
    }
    @PostMapping("/admin-create-event")
    public String createEvent(@ModelAttribute("event")@Valid EventCreationRequest request) throws Exception {
        eventService.createEvent(request);
        return "redirect:/admin/admin-events";
    }

    @GetMapping("/admin-update-event/{id}")
    public String showUpdateForm(@PathVariable int id,Model model){
        EventResponse event = eventService.getEvent(id);
        model.addAttribute("event",event);
        return "admin/admin-update-event";
    }

    @PostMapping("/admin-update-event/{id}")
    String updateEvent(@PathVariable int id, @ModelAttribute("event")
    @Valid EventUpdateRequest request){
        eventService.updateEvent(request,id);
        return "redirect:/admin/admin-events";
    }
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return "OK";
    }

}
