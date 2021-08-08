package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public class SecurityException extends CustomException{
    public SecurityException(String message) {
        super(message, ErrorType.SECURITY_ERROR_TYPE);
    }
}
