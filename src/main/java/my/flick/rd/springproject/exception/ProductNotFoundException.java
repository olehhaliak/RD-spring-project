package my.flick.rd.springproject.exception;

public class ProductNotFoundException extends NotFoundException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
