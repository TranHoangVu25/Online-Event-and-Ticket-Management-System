package com.ticketsystem.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin-home")
public class AdminDashBoardController {
    @GetMapping
    public String getOrder(){
        return "/admin/admin-dashboard";
    }
}
