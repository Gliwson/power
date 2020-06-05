package pl.power.services.exception;

public class IdIsNullException extends RuntimeException {
    public IdIsNullException() {
        super("Id is null ");
    }
}
