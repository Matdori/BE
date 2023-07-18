package com.example.beepoo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }

    //검증 시
    public static ResponseEntity<List<ErrorResponseEntity>> toResponseEntityValid(MethodArgumentNotValidException e){

        List<ErrorResponseEntity> errorResponseEntityList = new ArrayList<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {

            errorResponseEntityList.add(
                    ErrorResponseEntity.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .code(fieldError.getCode())
                            .field(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                            .build()
            );
        }
        return ResponseEntity
                .status(e.getStatusCode().value())
                .body(errorResponseEntityList);
    }
}