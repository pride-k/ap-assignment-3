package exceptions;

public class CantCancelError extends Exception {
    public CantCancelError(String message) {
        super(message);
    }
}