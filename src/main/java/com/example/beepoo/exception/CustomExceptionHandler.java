package com.example.beepoo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<ErrorResponseEntity>> handleValidationException(MethodArgumentNotValidException e) {
        return ErrorResponseEntity.toResponseEntityValid(e);
    }
}