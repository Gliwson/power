package pl.power.model;

import lombok.Data;
import pl.power.domain.entity.enums.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TaskDTO implements DTOInterface{

    private Long id;

    @NotBlank
    private String namePowerStation;

    @NotNull
    private TaskType taskType;

    @NotNull
    private BigDecimal powerLoss;

    @NotNull
    private Timestamp startDate;

    @NotNull
    private Timestamp endDate;
}
