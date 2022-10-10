package exceptions;

public class NoSuchElementsException extends RuntimeException{

    public NoSuchElementsException() {
    }

    public NoSuchElementsException(String message) {
        super(message);
    }
}
