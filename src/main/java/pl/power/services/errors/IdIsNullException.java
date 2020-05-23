package pl.power.services.errors;

public class IdIsNullException extends RuntimeException {
    public IdIsNullException() {
        super("Id is null ");
    }
}
