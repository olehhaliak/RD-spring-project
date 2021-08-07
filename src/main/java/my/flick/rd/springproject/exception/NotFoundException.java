package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public abstract class NotFoundException extends CustomException{
    public NotFoundException(String message) {
        super(message, ErrorType.DATABASE_ERROR_TYPE);
    }
}
