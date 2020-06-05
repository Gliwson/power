package pl.power.services.exception;

public class PowerStationDTONotFoundException extends RuntimeException {
    public PowerStationDTONotFoundException(Long id) {
        super("Could not find power stations DTO " + id);
    }
    public PowerStationDTONotFoundException() {
        super("Could not find power stations DTO");
    }
}
