package pl.power.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.power.dtos.CreateTaskDTO;
import pl.power.dtos.PowerStationDTO;
import pl.power.dtos.TaskDTO;
import pl.power.services.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createTask(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.add(createTaskDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PutMapping("/")
    public TaskDTO updateTask(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.update(createTaskDTO);
    }

    @GetMapping("/{id}/{taskType}")
    public Long getNumberOfEvents(@PathVariable Long id, @PathVariable String taskType) {
        return taskService.countEventsByIdPowerStation(id, taskType);
    }

}
