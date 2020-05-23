package pl.power.services;

import pl.power.dtos.PowerStationDTO;

import java.util.List;

public interface PowerStationService {

    Long add(PowerStationDTO powerStationDTO);

    List<PowerStationDTO> findAll();

    PowerStationDTO findById(Long id);

    PowerStationDTO update(Long id, PowerStationDTO powerStationDTO);

    void delete(Long id);
}
