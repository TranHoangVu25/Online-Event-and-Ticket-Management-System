package com.ticketsystem.dto.request;

import com.ticketsystem.entity.Location;
import com.ticketsystem.entity.User;
import com.ticketsystem.validator.EndDateConstraint;
import com.ticketsystem.validator.StartDateConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EndDateConstraint
public class EventCreationRequest {

    Location location;
    User creator;
    @Size(min = 3,message = "INVALID_EVENT_NAME")
    String name;
    String thumbnailUrl;
    String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @StartDateConstraint
    LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime endTime;
    boolean isActive = true;
    LocalDateTime createdAt = LocalDateTime.now();
}
