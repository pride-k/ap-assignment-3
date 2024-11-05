package exceptions;

public class OrderIsEmptyError extends Exception {
    public OrderIsEmptyError(String message) {
        super(message);
    }
}