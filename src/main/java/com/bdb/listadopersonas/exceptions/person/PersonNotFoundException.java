package com.bdb.listadopersonas.exceptions.person;

import com.bdb.listadopersonas.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class PersonNotFoundException extends ApiException {

    private static final long serialVersionUID = 1L;
    private static final String INTERNAL_ERROR_CODE = "person_not_found_exception";
    private static final String INTERNAL_DESCRIPTION ="PERSON NOT FOUND";

    public PersonNotFoundException() {
        super(INTERNAL_ERROR_CODE, INTERNAL_DESCRIPTION, HttpStatus.NOT_FOUND.value());
    }
}
