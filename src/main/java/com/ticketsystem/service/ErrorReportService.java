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

import java.util.List;

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
    public List<ErrorReport> getAllReport(){
        return errorReportRepository.findAll();
    }

    public ErrorReport getReportById(int id){
        return errorReportRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Error's id not found"));
    }

    public ErrorReport changeStatus(int id,String status){
        ErrorReport errorReport = errorReportRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Error's id not found"));
        errorReport.setStatus(status);
        return errorReportRepository.save(errorReport);
    }
    public ErrorReport changeStatus1(int id){
        ErrorReport errorReport = errorReportRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Error's id not found"));
        errorReport.setStatus("in_progress");
        return errorReportRepository.save(errorReport);
    }
    public ErrorReport changeStatus2(int id){
        ErrorReport errorReport = errorReportRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Error's id not found"));
        errorReport.setStatus("resolved");
        return errorReportRepository.save(errorReport);
    }
}
