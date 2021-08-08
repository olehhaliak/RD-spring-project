package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public class InputValidationException extends CustomException{
    public InputValidationException(String message) {
        super(message, ErrorType.VALIDATION_ERROR_TYPE);
    }
}
