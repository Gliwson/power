package pl.power.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.domain.entity.enums.TaskType;
import pl.power.domain.repository.PowerStationRepository;
import pl.power.services.exception.IdIsNullException;
import pl.power.services.exception.NotFoundIDException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class DefaultPowerStationServiceTest {

    @InjectMocks
    private DefaultPowerStationService service;

    @Mock
    private PowerStationRepository repository;

    @Mock
    private ModelMapper mapper;

    private PowerStation powerStation;

    @BeforeEach
    public void init() {
        Task task = new Task();
        task.setId(1L);
        task.setPowerLoss(new BigDecimal(100));
        task.setTaskType(TaskType.AWARIA);
        task.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"));
        task.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"));
        Task task1 = new Task();
        task1.setId(2L);
        task1.setPowerLoss(new BigDecimal(100));
        task1.setTaskType(TaskType.AWARIA);
        task1.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        task1.setEndDate(Timestamp.valueOf("2020-2-01 00:00:00"));
        Task task2 = new Task();
        task2.setId(3L);
        task2.setPowerLoss(new BigDecimal(100));
        task2.setTaskType(TaskType.REMONT);
        task2.setStartDate(Timestamp.valueOf("2019-12-31 23:00:00"));
        task2.setEndDate(Timestamp.valueOf("2020-01-01 00:00:00"));
        Task task3 = new Task();
        task3.setId(4L);
        task3.setPowerLoss(new BigDecimal(100));
        task3.setTaskType(TaskType.AWARIA);
        task3.setStartDate(Timestamp.valueOf("2020-01-25 00:00:00"));
        task3.setEndDate(Timestamp.valueOf("2020-2-01 00:00:00"));

        powerStation = new PowerStation();
        powerStation.setId(1L);
        powerStation.setName("Lublin");
        powerStation.setPower(new BigDecimal(200));
        powerStation.add(task);
        powerStation.add(task1);
        powerStation.add(task2);
        powerStation.add(task3);
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
        assertThrows(NotFoundIDException.class, () -> service.findById(2L));
        then(repository).should().findById(2L);
        then(mapper).should(never()).map(any(), any());
    }

    @Test
    void eventCountingMethodShouldCount() {
        //given
        given(repository.findAllOneSelect()).willReturn(Collections.singletonList(powerStation));
        //when
        Long result = service.countEventsByIdPowerStation(1L, "AWARIA");
        //then
        then(repository).should().findAllOneSelect();
        assertThat(result, is(3L));
    }

    @Test
    void shouldReturnMap() {
        //given
        given(repository.findAllOneSelect()).willReturn(Collections.singletonList(powerStation));
        //when
        Map<Long, BigDecimal> result = service.getDateAndPower("2010-12-01");
        //then
        then(repository).should().findAllOneSelect();
        assertThat(result.size(), is(1));
        assertThat(result.get(1L).abs(), is(new BigDecimal(200 * 24).setScale(2, RoundingMode.HALF_UP)));
    }


}
