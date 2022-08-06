package com.bdb.listadopersonas.exceptions;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
public class ApiErrorMessage extends ApiError{
    private List<?> errorMessages;

    public ApiErrorMessage(String error, String message, Integer status, List<?> errorMessages) {
        super(error, message, status);
        this.errorMessages = errorMessages;
    }

}
