package com.ticketsystem.dto.response;

import com.ticketsystem.validator.EndDateConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationResponse {
    String city;
    String addressDetail;
}

