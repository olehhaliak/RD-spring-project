package my.flick.rd.hw3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import my.flick.rd.hw3.exception.CustomException;
import my.flick.rd.hw3.model.enums.ErrorType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {

    public Error(String message, ErrorType errorType) {
        this.message = message;
        this.errorType = errorType;
        timestamp = LocalDateTime.now();
    }

    public Error(Exception exception){
      this(exception.getMessage(),ErrorType.FATAL_ERROR_TYPE);
    }

    public Error(CustomException exception) {
        this(exception.getMessage(),exception.errorType);
    }

    String message;
ErrorType errorType;
LocalDateTime timestamp;
}
