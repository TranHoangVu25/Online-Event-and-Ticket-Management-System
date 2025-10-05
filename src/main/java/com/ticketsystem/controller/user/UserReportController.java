package com.ticketsystem.controller.user;

import com.ticketsystem.dto.request.ErrorReportRequest;
import com.ticketsystem.entity.ErrorReport;
import com.ticketsystem.service.ErrorReportService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserReportController {

    ErrorReportService errorReportService;

    @GetMapping("/report-error")
    public String getReportError(Model model){
        model.addAttribute("errorReport",new ErrorReportRequest());
        return "/customer/report-error";
    }

    @PostMapping("/report-error")
    public String getReportError(
            HttpSession session,
            @ModelAttribute("errorReport") ErrorReportRequest request,
            RedirectAttributes redirectAttributes
            ){
        Integer userId = (Integer) session.getAttribute("userId");
        ErrorReport savedReport = errorReportService.createReport(userId,request);

        redirectAttributes.addFlashAttribute("errorId", savedReport.getId());

        return "redirect:/user/report-success";
    }

    @GetMapping("/report-success")
    public String getReportSuccess(Model model){

        return "/customer/report-success";
    }
}
