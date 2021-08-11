package my.flick.rd.springproject.exception;

public class UserIsBlockedException extends SecurityException{
    public UserIsBlockedException(String message) {
        super(message);
    }
}
