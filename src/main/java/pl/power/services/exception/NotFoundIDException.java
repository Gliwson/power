package pl.power.services.exception;

public class NotFoundIDException extends RuntimeException {
        public NotFoundIDException(Long id) {
        super("Not Found " + id);
    }
}
