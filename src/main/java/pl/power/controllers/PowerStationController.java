package pl.power.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.power.dtos.PowerStationDTO;
import pl.power.services.PowerStationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/powerstations")
public class PowerStationController {

    private final PowerStationService powerStationService;

    public PowerStationController(PowerStationService powerStationService) {
        this.powerStationService = powerStationService;
    }

    @GetMapping
    public List<PowerStationDTO> getPowerStations() {
        return powerStationService.findAll();
    }

    @GetMapping("/{id}")
    public PowerStationDTO getPowerStation(@PathVariable("id") Long id) {
        return powerStationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createPowerStation(@Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.add(powerStationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePowerStation(@PathVariable Long id) {
        powerStationService.delete(id);
    }

    @PutMapping("/{id}")
    public PowerStationDTO updateMovie(@PathVariable Long id, @Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.update(id, powerStationDTO);
    }


}
