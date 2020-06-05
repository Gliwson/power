package pl.power.validation;

import pl.power.model.CreateTaskDTO;

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
