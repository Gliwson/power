package pl.power.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.power.domain.entity.PowerStation;
import pl.power.model.PowerStationDTO;
import pl.power.services.exception.PowerStationDTONotFoundException;
import pl.power.services.exception.PowerStationNotFoundException;

@Component
public class PowerStationMapper implements MapperInterface<PowerStation, PowerStationDTO> {

    private final ModelMapper modelMapper;

    public PowerStationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PowerStation toEntity(PowerStationDTO powerStationDTO) {
        if(powerStationDTO == null){
            throw new PowerStationDTONotFoundException();
        }
        return modelMapper.map(powerStationDTO, PowerStation.class);
    }

    @Override
    public PowerStationDTO toDTO(PowerStation powerStation) {
        if(powerStation == null){
            throw new PowerStationNotFoundException();
        }
        return modelMapper.map(powerStation, PowerStationDTO.class);
    }
}
