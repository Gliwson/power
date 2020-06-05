package pl.power.services;

import org.springframework.data.domain.Pageable;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;

import java.util.List;

public interface TaskService {

    Long add(CreateTaskDTO createTaskDTO);

    void delete(Long id);

    List<TaskDTO> findAll(Pageable pageable);

    TaskDTO findById(Long id);

    Long countEventsByIdPowerStation(Long id, String taskType);

    TaskDTO update(CreateTaskDTO createTaskDTO);
}
