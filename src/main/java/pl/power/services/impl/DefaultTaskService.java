package pl.power.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.entities.Task;
import pl.power.domain.entities.enums.TaskType;
import pl.power.domain.repositories.PowerStationRepository;
import pl.power.domain.repositories.TaskRepository;
import pl.power.dtos.CreateTaskDTO;
import pl.power.dtos.TaskDTO;
import pl.power.services.TaskService;
import pl.power.services.errors.IdIsNullException;
import pl.power.services.errors.PowerStationsNotFoundException;
import pl.power.services.errors.TaskNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final PowerStationRepository powerStationRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public Long add(CreateTaskDTO createTaskDTO) {
        Task task = mapper.map(createTaskDTO, Task.class);
        Optional<PowerStation> powerStationOptional = powerStationRepository.findById(task.getId());
        PowerStation powerStation = powerStationOptional.orElseThrow(() -> new PowerStationsNotFoundException(task.getId()));
        task.setId(null);
        powerStation.add(task);
        powerStationRepository.save(powerStation);
        return taskRepository.findLastSaved();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IdIsNullException();
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAllOneSelect()
                .stream()
                .map(task -> {
                    TaskDTO map = mapper.map(task, TaskDTO.class);
                    map.setNamePowerStation(task.getPowerStation().getName());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long id) {
        if (id == null) {
            throw new IdIsNullException();
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return mapper.map(task, TaskDTO.class);
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
        Optional<Task> byId = taskRepository.findById(createTaskDTO.getId());
        Task task = byId.orElseThrow(() -> new TaskNotFoundException(createTaskDTO.getId()));
        task.setPowerLoss(createTaskDTO.getPowerLoss());
        task.setTaskType(createTaskDTO.getTaskType());
        task.setStartDate(createTaskDTO.getStartDate());
        task.setEndDate(createTaskDTO.getEndDate());
        Task save = taskRepository.save(task);
        return mapper.map(save,TaskDTO.class);
    }
}
