package my.flick.rd.springproject.exception;

public class OrderItemNotFoundException extends NotFoundException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
