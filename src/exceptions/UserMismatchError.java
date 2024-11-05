package exceptions;

public class UserMismatchError extends Exception {
    public UserMismatchError(String message) {
        super(message);
    }
}