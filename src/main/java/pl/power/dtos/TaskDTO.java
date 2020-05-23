package pl.power.dtos;

import lombok.Data;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.entities.enums.TaskType;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TaskDTO {

    private Long id;

    @NotBlank
    private String namePowerStation;

    @NotBlank
    private TaskType taskType;

    @NotBlank
    private BigDecimal powerLoss;

    @NotBlank
    private Timestamp startDate;

    @NotBlank
    private Timestamp endDate;
}
