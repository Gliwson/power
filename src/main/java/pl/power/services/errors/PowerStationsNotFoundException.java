package pl.power.services.errors;

public class PowerStationsNotFoundException extends RuntimeException {
    public PowerStationsNotFoundException(Long id) {
        super("Could not find power stations " + id);
    }
}
