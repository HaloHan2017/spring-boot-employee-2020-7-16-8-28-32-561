package com.thoughtworks.springbootemployee.enums;

public enum ExceptionMessage {

    DATA_NOT_FOUND(404, "Data Not Found"),
    ILLEGAL_OPERATION(404, "Illegal Operation");

    ExceptionMessage(int statusCode, String exceptionMsg) {
    }
}
