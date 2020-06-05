package pl.power.services.impl;

import javafx.util.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.power.calculator.DateCalculator;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.domain.entity.enums.TaskType;
import pl.power.domain.repository.PowerStationRepository;
import pl.power.domain.repository.TaskRepository;
import pl.power.domain.xmlDomain.Umm;
import pl.power.mapper.MapperInterface;
import pl.power.model.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.services.exception.IdIsNullException;
import pl.power.services.exception.PowerStationNotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultPowerStationService extends CrudAbstractService<PowerStation, PowerStationDTO> implements PowerStationService {

    private final PowerStationRepository powerStationRepository;
    private final TaskRepository taskRepository;
    private final ImportDataXmlService xmlService;

    public DefaultPowerStationService(JpaRepository<PowerStation, Long> jpaRepository,
                                      MapperInterface<PowerStation, PowerStationDTO> mapper,
                                      PowerStationRepository powerStationRepository,
                                      TaskRepository taskRepository, ImportDataXmlService xmlService) {
        super(jpaRepository, mapper);
        this.powerStationRepository = powerStationRepository;
        this.taskRepository = taskRepository;
        this.xmlService = xmlService;
    }

    @Override
    @Transactional
    public PowerStationDTO update(Long id, PowerStationDTO powerStationDTO) {
        if (id == null) {
            throw new IdIsNullException();
        }
        powerStationDTO.setId(id);
        powerStationRepository.findById(id)
                .orElseThrow(() -> new PowerStationNotFoundException(id));
        PowerStation map = mapper.toEntity(powerStationDTO);
        map.setId(id);
        powerStationRepository.save(map);
        return mapper.toDTO(map);
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
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    @Transactional
    public void addAll() {
        xmlService.getLinkToList().forEach(remitUMM -> {
            PowerStation powerStation = new PowerStation();
            if (!powerStationRepository.existsByName(remitUMM.getUmm().getAffectedAsset().getName())) {
                powerStation.setName(remitUMM.getUmm().getAffectedAsset().getName());
                powerStation.setPower(remitUMM.getUmm().getCapacity().getInstalledCapacity());
                powerStationRepository.save(powerStation);
            }

            if (!taskRepository.existsByStartDateAndEndDate(remitUMM.getUmm().getEvent().getEventStart(), remitUMM.getUmm().getEvent().getEventStop())) {
                powerStationRepository.findByName(remitUMM.getUmm().getAffectedAsset().getName())
                        .ifPresent(powerStation1 -> {
                            Umm umm = remitUMM.getUmm();
                            Task task = new Task();
                            task.setPowerLoss(umm.getCapacity().getUnavailableCapacity());
                            task.setStartDate(umm.getEvent().getEventStart());
                            task.setEndDate(umm.getEvent().getEventStop());
                            task.setTaskType(TaskType.AWARIA);
                            powerStation1.add(task);
                            powerStationRepository.save(powerStation1);
                        });
            }

        });
    }

}
