package my.flick.rd.hw3.exception;

import my.flick.rd.hw3.model.enums.ErrorType;

public abstract class CustomException extends RuntimeException{
    public final ErrorType errorType;

    public CustomException(String message,ErrorType type) {
        super(message);
        this.errorType = type;
    }
}
