package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.dto.response.RevenueResponse;
import com.ticketsystem.service.EventService;
import com.ticketsystem.service.RevenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin-home")
public class AdminDashBoardController {
    EventService eventService;
    RevenueService revenueService;

    @GetMapping
    public String getOrder(Model model){
        List<EventResponse> events = eventService.getEvents();
        model.addAttribute("events",events);
        RevenueResponse revenueResponse = revenueService.getMonthlyRevenueByPeriod("year");
        model.addAttribute("revenueData", revenueResponse);
        model.addAttribute("activePeriod", "year");

        return "/admin/admin-dashboard";
    }
}
