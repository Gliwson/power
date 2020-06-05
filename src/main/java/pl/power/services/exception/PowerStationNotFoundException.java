package pl.power.services.exception;

public class PowerStationNotFoundException extends RuntimeException {
    public PowerStationNotFoundException(Long id) {
        super("Could not find power stations " + id);
    }
    public PowerStationNotFoundException() {
        super("Could not find power stations");
    }
}
