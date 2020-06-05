package pl.power.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.power.domain.entity.PowerStation;

import java.util.Collection;
import java.util.Optional;

public interface PowerStationRepository extends JpaRepository<PowerStation, Long> {

    @Query("select distinct ps from PowerStation ps left join fetch ps.tasks")
    Collection<PowerStation> findAllOneSelect();

    boolean existsByName(String name);

    Optional<PowerStation> findByName(String name);

}
