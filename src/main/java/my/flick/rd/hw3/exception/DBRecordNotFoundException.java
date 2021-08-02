package my.flick.rd.hw3.exception;

import my.flick.rd.hw3.model.enums.ErrorType;

public class DBRecordNotFoundException extends CustomException{
    public DBRecordNotFoundException(String message) {
        super(message, ErrorType.DATABASE_ERROR_TYPE);
    }
}
