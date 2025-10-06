package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.response.RevenueResponse;
import com.ticketsystem.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping("/revenue-chart")
    public String getRevenueChart(@RequestParam(defaultValue = "year") String period, Model model) {
        RevenueResponse response = revenueService.getMonthlyRevenueByPeriod(period);
        model.addAttribute("revenueData", response);
        model.addAttribute("activePeriod", period);
        return "admin/revenue-chart-fragment :: revenue-chart";
    }

}