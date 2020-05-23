package pl.power.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.repositories.PowerStationRepository;
import pl.power.dtos.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.services.errors.IdIsNullException;
import pl.power.services.errors.PowerStationsNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
}
