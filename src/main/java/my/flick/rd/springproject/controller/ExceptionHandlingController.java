package my.flick.rd.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.exception.NotFoundException;
import my.flick.rd.springproject.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception){
       return new ResponseEntity<>(new Error(exception),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleException(NotFoundException exception){
        return new ResponseEntity<>(new Error(exception),HttpStatus.NOT_FOUND);
    }
}
