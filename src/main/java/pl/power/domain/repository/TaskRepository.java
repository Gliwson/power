package pl.power.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.power.domain.entity.Task;

import java.sql.Timestamp;
import java.util.Collection;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t left join fetch t.powerStation")
    Collection<Task> findAllOneSelect();

    @Query("select MAX(t.id) from Task t")
    Long findLastSaved();

    boolean existsByStartDateAndEndDate(Timestamp startDate,Timestamp endDate);
}
