package pl.power.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Converter {
    private Long id;
    private BigDecimal power;
}
