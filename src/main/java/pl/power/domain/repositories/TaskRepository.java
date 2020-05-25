package pl.power.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.power.domain.entities.PowerStation;
import pl.power.domain.entities.Task;

import java.util.Collection;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t left join fetch t.powerStation")
    Collection<Task> findAllOneSelect();

    @Query("select MAX(t.id) from Task t")
    Long findLastSaved();
}
