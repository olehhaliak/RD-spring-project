package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public class SelfReferencingException extends CustomException{
    public SelfReferencingException(String message) {
        super(message, ErrorType.DATABASE_ERROR_TYPE);
    }
}
