package exceptions;

public class ItemNotAvailableError extends Exception {
    public ItemNotAvailableError(String message) {
        super(message);
    }
}