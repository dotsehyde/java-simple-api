package com.dotsehyde.simpleapi.Models;

import java.util.HashMap;

public class ErrorModel {
    private int status;
    private String message;
    private HashMap<String,Object> details ;

    public ErrorModel() {
    }

    public ErrorModel(int status, String message) {
        this.status = status;
        this.message = message;
        this.details = new HashMap<>();
    }

    // getters and setters
    public String getMessage(){
        return message;
    }

    //Methods
    public void addDetail(String key, Object value){
        details.put(key, value);
    }
}