package com.ticketsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrCode {
    INVALID_EVENT_NAME("Event name at least 3 character")

    ;
    private String message;
}
