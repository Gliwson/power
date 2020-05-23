package pl.power.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.power.domain.entities.PowerStation;

import java.util.List;

public interface PowerStationRepository extends JpaRepository<PowerStation, Long> {

    @Query(value = "select ps from PowerStation ps left join fetch ps.tasks")
    List<PowerStation> findAll();
}
