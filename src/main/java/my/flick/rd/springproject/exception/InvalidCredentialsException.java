package my.flick.rd.springproject.exception;

public class InvalidCredentialsException extends SecurityException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
