package com.ticketsystem.exception;

public enum Error {
    COUPON_EXISTED("Coupon is existed"),
    COUPON_NOT_EXISTED("Coupon is not existed"),
    CONDITION_MIN("Condition must be >= 0"),
    CODE_MIN("Coupon code min length = 5"),
    ;
    Error(String message) {
    }
}
