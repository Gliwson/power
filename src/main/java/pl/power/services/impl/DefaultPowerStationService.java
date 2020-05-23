package pl.power.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.repositories.PowerStationRepository;
import pl.power.dtos.PowerStationDTO;
import pl.power.services.PowerStationService;

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
    public Long addPowerStation(PowerStationDTO powerStationDTO) {
        return null;
    }

    @Override
    public List<PowerStationDTO> findAll() {
        return powerStationRepository.findAll()
                .stream()
                .map(powerStation -> mapper.map(powerStation, PowerStationDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PowerStationDTO findById(Long id) {
        PowerStation powerStation = powerStationRepository.getOne(id);
        PowerStationDTO powerStationDTO = mapper.map(powerStation, PowerStationDTO.class);
        return powerStationDTO;
    }

    @Override
    @Transactional
    public PowerStationDTO updatePowerStation(Long id, PowerStationDTO powerStationDTO) {
        return null;
    }

    @Override
    @Transactional
    public void deletePowerStationById(Long id) {
        powerStationRepository.deleteById(id);
    }
}
