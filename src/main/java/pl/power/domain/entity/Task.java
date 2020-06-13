package pl.power.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.power.domain.entity.enums.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"powerStation"})
public class Task implements Serializable, EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_power_station")
    private PowerStation powerStation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_type")
    private TaskType taskType;

    @Column(name = "power_loss")
    private BigDecimal powerLoss;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;


}
