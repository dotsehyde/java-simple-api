package com.dotsehyde.simpleapi.Models;

import java.util.HashMap;

public class ErrorModel {
    public int status;
    public String message;
    public HashMap<String,Object> details ;

    public ErrorModel() {
    }

    public ErrorModel(int status, String message) {
        this.status = status;
        this.message = message;
        this.details = new HashMap<>();
    }

    // getters and setters

    //Methods
    public void addDetail(String key, Object value){
        details.put(key, value);
    }
}