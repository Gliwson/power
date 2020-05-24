package pl.power.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.domain.entities.enums.TaskType;
import pl.power.domain.repositories.TaskRepository;
import pl.power.dtos.TaskDTO;
import pl.power.services.TaskService;
import pl.power.services.errors.IdIsNullException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> findAllTasks() {
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
        return null;
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
}
