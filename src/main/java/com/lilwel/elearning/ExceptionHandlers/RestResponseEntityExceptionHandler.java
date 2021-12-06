package com.lilwel.elearning.ExceptionHandlers;

import com.lilwel.elearning.Handlers.ResponseHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap =  fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );
        return ResponseHandler.handleResponse("Error",HttpStatus.BAD_REQUEST,errorsMap.isEmpty()? ex:errorsMap);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        final List<FieldError> fieldErrors = ex.getMessage();
//        Map<String, Set<String>> errorsMap =  fieldErrors.stream().collect(
//                Collectors.groupingBy(FieldError::getField,
//                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
//                )
//        );
        return ResponseHandler.handleResponse("Error",HttpStatus.BAD_REQUEST,ex.getMessage());
    }
}