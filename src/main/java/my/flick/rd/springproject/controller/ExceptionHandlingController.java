package my.flick.rd.springproject.controller;

import lombok.extern.slf4j.Slf4j;
import my.flick.rd.springproject.exception.*;
import my.flick.rd.springproject.model.Error;
import my.flick.rd.springproject.model.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(SelfReferencingException.class)
    public ResponseEntity<Error> handleException(SelfReferencingException exception){
        return new ResponseEntity<>(new Error(exception),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleException(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), ErrorType.VALIDATION_ERROR_TYPE),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleException(HttpMessageNotReadableException exception){
        return new ResponseEntity<>(new Error(exception.getMessage(), ErrorType.VALIDATION_ERROR_TYPE),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthentificatedException.class)
    public ResponseEntity<Error> handleException(UserNotAuthentificatedException exception){
        return new ResponseEntity<>(new Error(exception),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AdminPrivilegesRequiredException.class)
    public ResponseEntity<Error> handleException(AdminPrivilegesRequiredException exception){
        return new ResponseEntity<>(new Error(exception),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Error> handleException(InvalidCredentialsException exception){
        return new ResponseEntity<>(new Error(exception),HttpStatus.UNAUTHORIZED);
    }
}
