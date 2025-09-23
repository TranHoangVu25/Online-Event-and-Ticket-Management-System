package com.ticketsystem.controller.user;

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
@RequestMapping("/user")
public class UserReportController {

    @GetMapping("/report-error")
    public String getReportError(){
        return "/customer/report-error";
    }

    @GetMapping("/report-success")
    public String getReportSuccess(){
        return "/customer/report-success";
    }
}
