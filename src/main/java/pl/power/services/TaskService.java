package pl.power.services;

import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;

import java.util.List;

public interface TaskService {

    Long add(CreateTaskDTO createTaskDTO);

    void delete(Long id);

    List<TaskDTO> findAll();

    TaskDTO findById(Long id);

    Long countEventsByIdPowerStation(Long id, String taskType);

    TaskDTO update(CreateTaskDTO createTaskDTO);
}
