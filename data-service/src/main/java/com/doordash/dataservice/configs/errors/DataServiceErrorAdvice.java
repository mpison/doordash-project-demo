package com.doordash.dataservice.configs.errors;

import com.doordash.dataservice.exceptions.DataServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class DataServiceErrorAdvice {

    @ExceptionHandler(DataServiceException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorExceptions(
            Exception e
    ) {
        // casting the generic Exception e to DataServiceException
        DataServiceException customErrorException = (DataServiceException) e;

        HttpStatus status = customErrorException.getStatus();

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        customErrorException.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        customErrorException.getMessage(),
                        stackTrace,
                        customErrorException.getData()
                ),
                status
        );
    }
}

