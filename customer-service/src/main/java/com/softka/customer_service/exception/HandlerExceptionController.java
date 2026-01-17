package com.softka.customer_service.exception;

import com.softka.customer_service.constants.ClientConstants;
import com.softka.customer_service.model.dto.ResponseErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerExceptionController {


    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ResponseErrorDto> notFoundException(Exception exception) {
        ResponseErrorDto response = ResponseErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .error(ClientConstants.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<ResponseErrorDto> alreadyExistException(Exception exception){
        ResponseErrorDto response = ResponseErrorDto.builder()
                .code(HttpStatus.CONFLICT.value())
                .error(ClientConstants.ALREADY_EXIST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseErrorDto> messageNotReadableException(Exception exception){
        ResponseErrorDto response = ResponseErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(ClientConstants.BAD_REQUEST)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseErrorDto> constraintViolationException(ConstraintViolationException exception){
        ResponseErrorDto response = ResponseErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .error(ClientConstants.INVALID_INPUT_DATA)
                .message(exception.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", ")))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}
