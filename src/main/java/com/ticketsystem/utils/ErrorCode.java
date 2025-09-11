package com.ticketsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_EVENT_NAME("Event name at least 3 character"),
    UNAUTHENTICATED("Unauthenticated")

    ;
    private String message;
}
