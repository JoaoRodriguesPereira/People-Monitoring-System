package exceptions;

public class EmptyInputException extends Exception{
    public EmptyInputException() {
        super("Existem campos vazios!");
    }

    public EmptyInputException(String message) {
        super(message);
    }
}
