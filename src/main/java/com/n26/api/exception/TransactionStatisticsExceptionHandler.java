package com.n26.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TransactionStatisticsExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Void> httpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        if (ex.getCause() instanceof InvalidFormatException)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
