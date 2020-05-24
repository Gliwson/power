package pl.power.services;

import pl.power.dtos.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO addTask(TaskDTO taskDTO);

    void deleteTask(Long id);

    List<TaskDTO> findAllTasks();

    TaskDTO findById(Long id);

    Long countEventsByIdPowerStation(Long id, String taskType);
}
