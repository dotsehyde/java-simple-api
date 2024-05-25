package com.dotsehyde.simpleapi.Utils;

import org.springframework.http.HttpStatusCode;

public class AppException extends RuntimeException{
    private HttpStatusCode statusCode;
    private String message;

    public AppException(HttpStatusCode statusCode,String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
