package pl.power.services;

import pl.power.dtos.PowerStationDTO;

import java.util.List;

public interface PowerStationService {

    Long addPowerStation(PowerStationDTO powerStationDTO);

    List<PowerStationDTO> findAll();

    PowerStationDTO findById(Long id);

    PowerStationDTO updatePowerStation(Long id, PowerStationDTO powerStationDTO);

    void deletePowerStationById(Long id);
}
