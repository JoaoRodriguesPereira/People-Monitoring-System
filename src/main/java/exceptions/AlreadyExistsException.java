package exceptions;

public class AlreadyExistsException extends  Exception{
    public AlreadyExistsException() {
        super("Este elemento já existe");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
