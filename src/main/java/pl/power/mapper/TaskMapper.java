package pl.power.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.power.domain.entity.Task;
import pl.power.model.TaskDTO;
import pl.power.services.exception.TaskDTONotFoundException;
import pl.power.services.exception.TaskNotFoundException;

@Component
public class TaskMapper implements MapperInterface<Task, TaskDTO> {

    private final ModelMapper mapper;

    public TaskMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Task toEntity(TaskDTO taskDTO) {
        if(taskDTO == null){
            throw new TaskNotFoundException();
        }
        return mapper.map(taskDTO, Task.class);
    }

    @Override
    public TaskDTO toDTO(Task task) {
        if(task == null){
            throw new TaskDTONotFoundException();
        }
        TaskDTO map = mapper.map(task, TaskDTO.class);
        map.setNamePowerStation(task.getPowerStation().getName());
        return map;
    }
}
