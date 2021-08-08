package my.flick.rd.springproject.exception;

public class AdminPrivilegesRequiredException extends SecurityException {
    public AdminPrivilegesRequiredException(String message) {
        super(message);
    }
}
