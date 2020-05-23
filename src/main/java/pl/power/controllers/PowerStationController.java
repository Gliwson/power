package pl.power.controllers;

import org.springframework.web.bind.annotation.*;
import pl.power.dtos.PowerStationDTO;
import pl.power.services.PowerStationService;

import java.util.List;

@RestController
@RequestMapping("/powerstation")
public class PowerStationController {

    private final PowerStationService powerStationService;

    public PowerStationController(PowerStationService powerStationService) {
        this.powerStationService = powerStationService;
    }


    @PostMapping
    public Long addPowerStation(@RequestBody PowerStationDTO powerStationDTO) {

        return powerStationService.addPowerStation(powerStationDTO);
    }

    @GetMapping
    public List<PowerStationDTO> findAll() {
        return powerStationService.findAll();
    }

    @GetMapping("/{id}")
    public PowerStationDTO findById(@PathVariable Long id) {
        return powerStationService.findById(id);
    }

    @PutMapping("/{id}")
    public PowerStationDTO updatePowerStation(@PathVariable Long id, @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.updatePowerStation(id, powerStationDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePowerStation(@PathVariable Long id) {
        powerStationService.deletePowerStationById(id);
    }

}
