package com.ticketsystem.dto.request;

import com.ticketsystem.validator.EndDateConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EndDateConstraint
public class LocationRequest {
    String city;
    String addressDetail;
}
