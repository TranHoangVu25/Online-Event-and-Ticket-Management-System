package com.ticketsystem.dto.request;

import com.ticketsystem.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorReportRequest {
//    String errorId;
    User user;
    String contactEmail;
    String title;
    String description;
    String stepsToReproduce;
    String screenshotUrl;
    String status = "new";
    Boolean consentAttachInternal = false;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
