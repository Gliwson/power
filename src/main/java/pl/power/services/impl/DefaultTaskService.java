package pl.power.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.domain.entity.enums.TaskType;
import pl.power.domain.repository.PowerStationRepository;
import pl.power.domain.repository.TaskRepository;
import pl.power.mapper.MapperInterface;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;
import pl.power.services.TaskService;
import pl.power.services.exception.IdIsNullException;
import pl.power.services.exception.PowerStationNotFoundException;
import pl.power.services.exception.TaskNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultTaskService extends CrudAbstractService<Task, TaskDTO> implements TaskService {

    private final TaskRepository taskRepository;
    private final PowerStationRepository powerStationRepository;
    private final ModelMapper modelMapper;

    public DefaultTaskService(JpaRepository<Task, Long> jpaRepository, MapperInterface<Task, TaskDTO> mapper,
                              TaskRepository taskRepository, PowerStationRepository powerStationRepository,
                              ModelMapper modelMapper) {
        super(jpaRepository, mapper);
        this.taskRepository = taskRepository;
        this.powerStationRepository = powerStationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Long add(CreateTaskDTO createTaskDTO) {
        Task task = modelMapper.map(createTaskDTO, Task.class);
        Optional<PowerStation> powerStationOptional = powerStationRepository.findById(task.getId());
        PowerStation powerStation = powerStationOptional.orElseThrow(() -> new PowerStationNotFoundException(task.getId()));
        task.setId(null);
        powerStation.add(task);
        powerStationRepository.save(powerStation);
        return taskRepository.findLastSaved();
    }

    @Override
    public Long countEventsByIdPowerStation(Long id, String taskType) {
        if (id == null) {
            throw new IdIsNullException();
        }
        TaskType filter = TaskType.mapStringToTaskType(taskType);
        return taskRepository.findAllOneSelect()
                .stream()
                .filter(task -> task.getPowerStation().getId().equals(id))
                .filter(value -> value.getTaskType() == filter)
                .count();
    }

    @Override
    @Transactional
    public TaskDTO update(CreateTaskDTO createTaskDTO) {
        Task task = taskRepository.findById(createTaskDTO.getId()).orElseThrow(() -> new TaskNotFoundException(createTaskDTO.getId()));
        task.setPowerLoss(createTaskDTO.getPowerLoss());
        task.setTaskType(createTaskDTO.getTaskType());
        task.setStartDate(createTaskDTO.getStartDate());
        task.setEndDate(createTaskDTO.getEndDate());
        return mapper.toDTO(task);
    }

    @Override
    public List<TaskDTO> findAll(Pageable pageable) {
        List<Task> all = taskRepository.findAll(pageable).toList();
        List<Long> longList = all.stream()
                .map(value -> value.getPowerStation().getId())
                .collect(Collectors.toList());
        powerStationRepository.findAllById(longList);
        return mapper.toDTOs(all);
    }
}
