package my.flick.rd.springproject.exception;

import my.flick.rd.springproject.model.enums.ErrorType;

public class DBRecordNotFoundException extends CustomException{
    public DBRecordNotFoundException(String message) {
        super(message, ErrorType.DATABASE_ERROR_TYPE);
    }
}
