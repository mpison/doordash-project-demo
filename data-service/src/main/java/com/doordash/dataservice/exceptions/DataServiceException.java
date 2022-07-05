package com.doordash.dataservice.exceptions;

import org.springframework.http.HttpStatus;

public class DataServiceException extends RuntimeException {

    private HttpStatus status = null;

    private Object data = null;

    public DataServiceException(String message) {
        super(message);
    }

    public DataServiceException(
            HttpStatus status,
            String message
    ) {
        this(message);
        this.status = status;
    }

    public DataServiceException(
            HttpStatus status,
            String message,
            Object data
    ) {
        this(
                status,
                message
        );
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
