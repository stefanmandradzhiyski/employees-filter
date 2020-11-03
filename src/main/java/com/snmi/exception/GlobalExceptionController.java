package com.snmi.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
