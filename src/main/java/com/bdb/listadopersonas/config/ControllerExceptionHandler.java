package com.bdb.listadopersonas.config;

import com.bdb.listadopersonas.dto.ArgumentNotValidDTO;
import com.bdb.listadopersonas.exceptions.ApiError;
import com.bdb.listadopersonas.exceptions.ApiErrorMessage;
import com.bdb.listadopersonas.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
        ApiError apiError = new ApiError("route_not_found", String.format("Route %s not found",
                req.getRequestURI()),
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
        LOGGER.error("Internal error", e);

        ApiError apiError = new ApiError("internal_error", "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiError> handleApiException(ApiException e) {
        ApiError apiError = new ApiError(e.getCode(), e.getDescription(), e.getStatusCode());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ApiErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        LOGGER.error("Argument not valid error", e);

        List<ArgumentNotValidDTO> errors = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.add(new ArgumentNotValidDTO(fieldName, message));
        });

        ApiErrorMessage apiError = new ApiErrorMessage("method_argument_not_valid",
                "Arguments not valid",
                HttpStatus.BAD_REQUEST.value(), errors);

        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }


}
