package my.flick.rd.springproject.exception;

public class UserIsNotCustomerException extends SecurityException {
    public UserIsNotCustomerException(String message) {
        super(message);
    }
}
