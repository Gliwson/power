package pl.power.validation.validators;

import pl.power.dtos.CreateTaskDTO;
import pl.power.dtos.TaskDTO;
import pl.power.validation.constraints.StartDateAndEndDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartDateIsGreaterThanEndDate implements ConstraintValidator<StartDateAndEndDate, CreateTaskDTO> {

    public void initialize(StartDateAndEndDate constraint) {
    }

    public boolean isValid(CreateTaskDTO taskDTO, ConstraintValidatorContext context) {
        if (taskDTO.getStartDate() == null || taskDTO.getEndDate() == null) {
            return false;
        }
        return taskDTO.getStartDate().toLocalDateTime().isBefore(taskDTO.getEndDate().toLocalDateTime());
    }
}
