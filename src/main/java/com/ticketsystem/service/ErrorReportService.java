package com.ticketsystem.service;

import com.ticketsystem.dto.request.ErrorReportRequest;
import com.ticketsystem.entity.ErrorReport;
import com.ticketsystem.entity.User;
import com.ticketsystem.repository.ErrorReportRepository;
import com.ticketsystem.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ErrorReportService {
    ErrorReportRepository errorReportRepository;
    UserRepository userRepository;

    public ErrorReport createReport(int userId, ErrorReportRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("In create report. UserId not found"));

        ErrorReport errorReport = new ErrorReport().builder()
                .user(user)
                .contactEmail(request.getContactEmail())
                .title(request.getTitle())
                .description(request.getDescription())
                .stepsToReproduce(request.getStepsToReproduce())
                .screenshotUrl(request.getScreenshotUrl())
                .status(request.getStatus())
                .consentAttachInternal(request.getConsentAttachInternal())
                .build();
        return errorReportRepository.save(errorReport);
    }
}
