package com.bdb.listadopersonas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DeleteResponse {
    private String message;

    public DeleteResponse(String message) {

    }
}
