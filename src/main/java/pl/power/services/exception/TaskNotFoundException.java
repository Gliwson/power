package pl.power.services.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Could not find task " + id);
    }
    public TaskNotFoundException() {
        super("Could not find task");
    }
}
