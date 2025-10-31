package com.ticketsystem.controller.admin;

import com.ticketsystem.entity.ErrorReport;
import com.ticketsystem.service.ErrorReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminErrorReportController {
    ErrorReportService errorReportService;


    @GetMapping("/error-reports")
    public String getErrors(Model model) {
        List<ErrorReport> errorReports = errorReportService.getAllReport();
        model.addAttribute("errorReports", errorReports);

        //add trường này để active trong ui xem đang ở trong phần nào trong sidebar
        model.addAttribute("activePage","reports");
        return "admin/admin-error-reports";
    }

    @GetMapping("/error-report-detail/{errId}")
    public String getErrorDetailById(
            @PathVariable int errId,
            Model model
    ) {
        ErrorReport errorReport = errorReportService.getReportById(errId);
        model.addAttribute("er", errorReport);
        model.addAttribute("activePage","reports");
        return "admin/admin-error-reports-detail";
    }

    @PostMapping("/error-report/change-status")
    public String changeStatus(
            @RequestParam int id,
            @RequestParam String status,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ErrorReport updatedReport = errorReportService.changeStatus(id, status);
            redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật trạng thái thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        // Redirect về trang danh sách error reports
        return "redirect:/admin/error-reports";
    }
}
