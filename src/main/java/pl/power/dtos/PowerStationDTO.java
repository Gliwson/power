package pl.power.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class PowerStationDTO {

    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String name;

    @NotNull
    private BigDecimal power;

}
