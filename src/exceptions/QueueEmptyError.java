package exceptions;

public class QueueEmptyError extends Exception {
    public QueueEmptyError(String message) {
        super(message);
    }
}