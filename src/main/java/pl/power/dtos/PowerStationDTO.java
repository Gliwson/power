package pl.power.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class PowerStationDTO {

    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String name;

    @NotBlank
    private BigDecimal power;

    private Set<TaskDTO> tasks;

}
