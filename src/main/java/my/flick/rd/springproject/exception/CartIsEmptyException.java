package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public class CartIsEmptyException extends CustomException{
    public CartIsEmptyException(String message) {
        super(message, ErrorType.PROCESSING_ERROR_TYPE);
    }
}
