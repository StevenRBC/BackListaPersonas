package com.bdb.listadopersonas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArgumentNotValidDTO {
    private String FieldName;
    private String Message;
}
