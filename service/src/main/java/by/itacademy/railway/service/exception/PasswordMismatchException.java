package by.itacademy.railway.service.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        this("Incorrect password.");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
