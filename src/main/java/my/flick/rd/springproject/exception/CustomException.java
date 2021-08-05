package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public abstract class CustomException extends RuntimeException{
    public final ErrorType errorType;

    public CustomException(String message,ErrorType type) {
        super(message);
        this.errorType = type;
    }
}
