package exceptions;

public class LoginFailedError extends Exception {
    public LoginFailedError(String message) {
        super(message);
    }
}