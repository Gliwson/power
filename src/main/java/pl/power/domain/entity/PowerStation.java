package pl.power.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "power_stations")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"tasks"})
public class PowerStation implements Serializable, EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Column(name = "power")
    private BigDecimal power;

    @OneToMany(mappedBy = "powerStation", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public void add(Task task) {
        if (tasks == null) {
            tasks = new HashSet<>();
        }
        tasks.add(task);
        task.setPowerStation(this);
    }


}
