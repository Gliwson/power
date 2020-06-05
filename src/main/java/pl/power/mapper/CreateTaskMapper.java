package pl.power.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.power.domain.entity.Task;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;
import pl.power.services.exception.TaskDTONotFoundException;
import pl.power.services.exception.TaskNotFoundException;

@Component
public class CreateTaskMapper implements MapperInterface<Task, CreateTaskDTO> {

    private final ModelMapper mapper;

    public CreateTaskMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Task toEntity(CreateTaskDTO taskDTO) {
        if(taskDTO == null){
            throw new TaskNotFoundException();
        }
        return mapper.map(taskDTO, Task.class);
    }

    @Override
    public CreateTaskDTO toDTO(Task task) {
        if(task == null){
            throw new TaskDTONotFoundException();
        }
        return mapper.map(task, CreateTaskDTO.class);
    }
}
