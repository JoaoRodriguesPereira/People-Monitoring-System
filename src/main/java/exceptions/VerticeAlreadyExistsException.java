package exceptions;

public class VerticeAlreadyExistsException extends Exception{

    public VerticeAlreadyExistsException() {
    }

    public VerticeAlreadyExistsException(String message) {
        super(message);
    }
}
