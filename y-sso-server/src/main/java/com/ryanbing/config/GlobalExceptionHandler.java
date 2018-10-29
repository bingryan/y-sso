package com.ryanbing.config;

import com.ryanbing.entity.ResponseResult;
import com.ryanbing.exception.YSsoException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 全局异常
 *
 * @author ryan
 **/

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {


    /**
     * 系统顶层业务异常
     */
    @ExceptionHandler(YSsoException.class)
    public ResponseResult ySsoException(YSsoException ae) {
        return ResponseResult.error(ae.getMessage());
    }


    @ExceptionHandler(value = {BindException.class})
    public ResponseResult bindException(BindException ex) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return ResponseResult.error(String.join(",", errors));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseResult constraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return ResponseResult.error(String.join(",", errorMessages));
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        return ResponseResult.error("系统异常");
    }


}
