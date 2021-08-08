package my.flick.rd.springproject.exception;

public class UserNotAuthentificatedException extends SecurityException {
    public UserNotAuthentificatedException(String message) {
        super(message);
    }
}
