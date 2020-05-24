package pl.power.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.power.domain.entities.PowerStation;

import java.util.Collection;
import java.util.List;

public interface PowerStationRepository extends JpaRepository<PowerStation, Long> {

    @Query("select distinct ps from PowerStation ps join fetch ps.tasks")
    Collection<PowerStation> findAllOneSelect();
}
