package exceptions;

public class OrderNotFoundError extends Exception {
    public OrderNotFoundError(String message) {
        super(message);
    }
}