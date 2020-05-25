package pl.power.services.impl;

import ch.qos.logback.classic.pattern.DateConverter;
import javafx.scene.effect.FloatMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.calculator.DateCalculator;
import pl.power.domain.Converter;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.entities.Task;
import pl.power.domain.entities.enums.TaskType;
import pl.power.domain.repositories.PowerStationRepository;
import pl.power.dtos.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.services.errors.IdIsNullException;
import pl.power.services.errors.PowerStationsNotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultPowerStationService implements PowerStationService {

    private final PowerStationRepository powerStationRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public Long add(PowerStationDTO powerStationDTO) {
        PowerStation powerStation = mapper.map(powerStationDTO, PowerStation.class);
        powerStation.setId(null);
        return powerStationRepository.save(powerStation).getId();
    }

    @Override
    public List<PowerStationDTO> findAll() {
        return powerStationRepository.findAll()
                .stream()
                .map(powerStation -> mapper.map(powerStation, PowerStationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PowerStationDTO findById(Long id) {
        if (id == null) {
            throw new IdIsNullException();
        }
        PowerStation powerStation = powerStationRepository.findById(id)
                .orElseThrow(() -> new PowerStationsNotFoundException(id));
        return mapper.map(powerStation, PowerStationDTO.class);
    }

    @Override
    @Transactional
    public PowerStationDTO update(Long id, PowerStationDTO powerStationDTO) {
        if (id == null) {
            throw new IdIsNullException();
        }
        powerStationDTO.setId(id);
        powerStationRepository.findById(id)
                .orElseThrow(() -> new PowerStationsNotFoundException(id));
        PowerStation map = mapper.map(powerStationDTO, PowerStation.class);
        map.setId(id);
        powerStationRepository.save(map);
        return mapper.map(map, PowerStationDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IdIsNullException();
        }
        powerStationRepository.deleteById(id);
    }

    @Override
    public Long countEventsByIdPowerStation(Long id, String taskType) {
        if (id == null) {
            throw new IdIsNullException();
        }
        TaskType filter = TaskType.mapStringToTaskType(taskType);
        return powerStationRepository.findAllOneSelect().stream()
                .filter(powerStation -> powerStation.getId().equals(id))
                .map(PowerStation::getTasks)
                .flatMap(Collection::stream)
                .filter(task -> task.getTaskType() == filter)
                .count();
    }

    @Override
    public Map<Long, BigDecimal> getDateAndPower(String date) {
        DateCalculator dateCalculator = new DateCalculator(date);
        return powerStationRepository.findAllOneSelect().stream()
                .map(dateCalculator::subtractPowerLossFromPower)
                .collect(Collectors.toMap(Converter::getId, Converter::getPower));
    }

}
