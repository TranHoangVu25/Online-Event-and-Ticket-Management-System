package com.ticketsystem.exception;

public enum Error {
    COUPON_EXISTED("Coupon is existed"),
    COUPON_NOT_EXISTED("Coupon is not existed")
    ;
    Error(String name) {
    }
}
