package com.bdb.listadopersonas.exceptions.person;

import com.bdb.listadopersonas.exceptions.ApiException;
import org.springframework.http.HttpStatus;


public class PersonAlreadyExist extends ApiException {

    private static final long serialVersionUID = 1L;
    private static final String INTERNAL_ERROR_CODE = "person_already_exist";
    private static final String INTERNAL_DESCRIPTION ="PERSON ALREADY EXIST";

    public PersonAlreadyExist() {
        super(INTERNAL_ERROR_CODE, INTERNAL_DESCRIPTION, HttpStatus.CONFLICT.value());
    }
}
