package pl.power.calculator;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.domain.entity.enums.TaskType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DateCalculatorTest {

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
    public void shouldReturnIdPowerStation() {
        //given
        DateCalculator dateCalculator = new DateCalculator("2020-01-25");
        //when
        Pair<Long,BigDecimal> result = dateCalculator.subtractPowerLossFromPower(powerStation);
        //then
        assertThat(result.getKey(), is(1L));

    }

    @Test
    public void powerShouldBeSame() {
        //given
        DateCalculator dateCalculator = new DateCalculator("2020-06-26");
        //when
        Pair<Long,BigDecimal> result = dateCalculator.subtractPowerLossFromPower(powerStation);
        //then
        assertThat(result.getValue(), is(new BigDecimal(200 * 24).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void powerShouldZero() {
        //given
        DateCalculator dateCalculator = new DateCalculator("2020-01-25");
        //when
        Pair<Long,BigDecimal> result = dateCalculator.subtractPowerLossFromPower(powerStation);
        //then
        assertThat(result.getValue(), is(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void powerShouldBeAHundredLower() {
        //given
        DateCalculator dateCalculator = new DateCalculator("2019-12-31");
        //when
        Pair<Long,BigDecimal> result = dateCalculator.subtractPowerLossFromPower(powerStation);
        //then
        assertThat(result.getValue(), is(new BigDecimal((200 * 24) - 100).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void shouldBeAbleCountInTheMiddleOfTheDay() {
        //given
        DateCalculator dateCalculator = new DateCalculator("2018-01-20");
        //when
        Pair<Long,BigDecimal> result = dateCalculator.subtractPowerLossFromPower(powerStation);
        //then
        assertThat(result.getValue(), is(new BigDecimal((200 * 24) - 100).setScale(2, RoundingMode.HALF_UP)));
    }

}
