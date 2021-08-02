package my.flick.rd.hw3.controller;

import my.flick.rd.hw3.exception.DBRecordNotFoundException;
import my.flick.rd.hw3.model.Error;
import my.flick.rd.hw3.model.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception){
       return new ResponseEntity<>(new Error(exception),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DBRecordNotFoundException.class)
    public ResponseEntity<Error> handleException(DBRecordNotFoundException exception){
        return new ResponseEntity<>(new Error(exception),HttpStatus.NOT_FOUND);
    }
}
