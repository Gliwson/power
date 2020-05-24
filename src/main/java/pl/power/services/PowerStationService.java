package pl.power.services;

import pl.power.dtos.PowerStationDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PowerStationService {

    Long add(PowerStationDTO powerStationDTO);

    List<PowerStationDTO> findAll();

    PowerStationDTO findById(Long id);

    PowerStationDTO update(Long id, PowerStationDTO powerStationDTO);

    void delete(Long id);

    Long countEventsByIdPowerStation(Long id, String taskType);

    Map<Integer, BigDecimal> getDateAndPower(String date);
}
