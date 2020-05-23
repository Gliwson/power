package pl.power.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "power_stations")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"tasks"})
public class PowerStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "power")
    private BigDecimal power;

    @OneToMany(mappedBy = "powerStation", cascade = CascadeType.ALL)
    private Set<Task> tasks;
}
