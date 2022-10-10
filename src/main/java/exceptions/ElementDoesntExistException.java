package exceptions;

public class ElementDoesntExistException extends Exception{

    public ElementDoesntExistException() {
    }

    public ElementDoesntExistException(String message) {
        super(message);
    }
}
