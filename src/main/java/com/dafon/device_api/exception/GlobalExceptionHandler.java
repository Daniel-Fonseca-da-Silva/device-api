package com.dafon.device_api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ProblemDetail handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            return new InvalidFieldException().toProblemDetail();
        }
        return ProblemDetail.forStatus(400);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ProblemDetail handleCustomException(CustomException ex) {
        return ex.toProblemDetail();
    }
} 