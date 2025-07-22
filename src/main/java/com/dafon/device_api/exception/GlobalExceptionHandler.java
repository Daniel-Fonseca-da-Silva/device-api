package com.dafon.device_api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ProblemDetail handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            logger.warn("Attempted operation with wrong fields");
            return new InvalidFieldException().toProblemDetail();
        }
        return ProblemDetail.forStatus(400);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ProblemDetail handleCustomException(CustomException ex) {
        return ex.toProblemDetail();
    }

    @ExceptionHandler(DeviceInUseException.class)
    @ResponseBody
    public ProblemDetail handleDeviceInUseException(DeviceInUseException ex) {
        logger.warn("Attempted operation on device in use");
        return ex.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        logger.warn("Validation failed for argument: {}", ex.getMessage());
        return new InvalidFieldException().toProblemDetail();
    }
}
