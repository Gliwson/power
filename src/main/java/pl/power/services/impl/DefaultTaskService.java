package pl.power.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.domain.repositories.TaskRepository;
import pl.power.dtos.TaskDTO;
import pl.power.services.TaskService;

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
        taskRepository.findById(id).ifPresent(taskRepository::delete);
    }

    @Override
    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll()
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
}
