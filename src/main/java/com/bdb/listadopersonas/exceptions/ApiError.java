package com.bdb.listadopersonas.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String error;
    private String message;
    private Integer status;
    private List<String> messages;

    public ApiError(){
    }

    public ApiError(String error, String message, Integer status) {
        this.error = error;
        this.message= message;
        this.status = status;
    }

    public ApiError(String error, Integer status, List<String> messages) {
        this.error = error;
        this.status = status;
        this.messages= messages;
    }
}
