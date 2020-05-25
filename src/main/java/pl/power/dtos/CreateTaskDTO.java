package pl.power.dtos;

import lombok.Data;
import pl.power.domain.entities.enums.TaskType;
import pl.power.validation.constraints.StartDateAndEndDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@StartDateAndEndDate
public class CreateTaskDTO {

    @NotNull
    private Long id;

    @NotNull
    private TaskType taskType;

    @NotNull
    private BigDecimal powerLoss;

    @NotNull
    private Timestamp startDate;

    @NotNull
    private Timestamp endDate;
}
