package com.dotsehyde.simpleapi.Utils;

import com.dotsehyde.simpleapi.Models.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorModel> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            //String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ErrorModel(400, errorMessage));
        });

        return new ResponseEntity<>(errors.get(0), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorModel> handleDatabaseExceptions(AppException ex) {
        var error = new ErrorModel(ex.getStatusCode().value(),ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
}


