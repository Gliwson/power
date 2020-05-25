package pl.power.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.entities.Task;
import pl.power.domain.entities.enums.TaskType;
import pl.power.domain.repositories.TaskRepository;
import pl.power.dtos.CreateTaskDTO;
import pl.power.dtos.TaskDTO;
import pl.power.services.errors.IdIsNullException;
import pl.power.services.errors.TaskNotFoundException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class DefaultTaskServiceTest {

    @InjectMocks
    private DefaultTaskService service;

    @Mock
    private TaskRepository repository;

    @Mock
    private ModelMapper mapper;

    private Task task;
    private PowerStation powerStation;
    private TaskDTO taskDTO;
    private CreateTaskDTO createTaskDTO;

    @BeforeEach
    public void init() {
        taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setNamePowerStation("Lublin");
        taskDTO.setPowerLoss(new BigDecimal(100));
        taskDTO.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"));
        taskDTO.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"));

        task = new Task();
        task.setId(1L);
        task.setPowerLoss(new BigDecimal(100));
        task.setTaskType(TaskType.AWARIA);
        task.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"));
        task.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"));

        powerStation = new PowerStation();
        powerStation.setId(1L);
        powerStation.setName("Lublin");
        powerStation.setPower(new BigDecimal(200));
        powerStation.add(task);

        createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setId(1L);
        createTaskDTO.setPowerLoss(new BigDecimal(100));
        createTaskDTO.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"));
        createTaskDTO.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"));
    }

    @Test
    void shouldFindTaskDtoById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(task));
        given(mapper.map(task, TaskDTO.class)).willReturn(taskDTO);
        //when
        TaskDTO result = service.findById(1L);
        //then
        then(repository).should().findById(1L);
        assertThat(result.getNamePowerStation(), equalTo("Lublin"));
        assertThat(result.getPowerLoss(), is(new BigDecimal(100)));
    }

    @Test
    void ifIdIsNullShouldThrowException() {
        //given
        //when
        //then
        assertThrows(IdIsNullException.class, () -> service.findById(null));
        then(repository).should(never()).findById(anyLong());
        then(mapper).should(never()).map(any(), any());
    }

    @Test
    void ifIdIsNotFoundShouldThrowException() {
        //given
        //when
        //then
        assertThrows(TaskNotFoundException.class, () -> service.findById(2L));
        then(repository).should().findById(2L);
        then(mapper).should(never()).map(any(), any());
    }

    @Test
    void eventCountingMethodShouldCount() {
        //given
        given(repository.findAllOneSelect()).willReturn(Collections.singletonList(task));
        //when
        Long result = service.countEventsByIdPowerStation(1L, "AWARIA");
        //then
        then(repository).should().findAllOneSelect();
        assertThat(result, is(1L));
    }

    @Test
    void shouldBeAbleUpdateTask() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(task));
        given(repository.save(task)).willReturn(task);
        given(mapper.map(task, TaskDTO.class)).willReturn(taskDTO);
        //when
        TaskDTO result = service.update(createTaskDTO);
        //then
        then(repository).should().findById(anyLong());
        then(repository).should().save(ArgumentMatchers.any());
        then(mapper).should().map(ArgumentMatchers.any(), ArgumentMatchers.any());
        assertThat(result.getPowerLoss(), is(taskDTO.getPowerLoss()));
    }
}
