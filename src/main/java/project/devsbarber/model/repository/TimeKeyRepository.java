package project.devsbarber.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.TimeKey;

import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional
public interface TimeKeyRepository extends CrudRepository<TimeKey, Long> {

    @Query(value = "select tk from TimeKey tk where tk.key >= :init and tk.key <= :final ")
    List<TimeKey> findByInitialAndFinalKeys(@Param("init") Long initHour, @Param("final") Long finalHours);

    @Query(value = "select tk from TimeKey tk where tk.time >= :init and tk.time <= :final ")
    List<TimeKey> findByInitialAndFinalTime(@Param("init") LocalTime initHour, @Param("final") LocalTime finalHours);

    @Query(value = "select tk from TimeKey tk where tk.time = :time ")
    TimeKey getByTime(@Param("time") LocalTime workEndTime);
}
