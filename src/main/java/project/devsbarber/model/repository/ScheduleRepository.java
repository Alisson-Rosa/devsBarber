package project.devsbarber.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.Schedule;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends CrudRepository <Schedule, Long> {
    List<Schedule> findByBarber(Barber barberId);

    @Query(value = "SELECT s FROM Schedule s WHERE s.barber = :barber and s.date = :date")
    List<Schedule> findByBarberAndDate(@Param("barber") Barber barber, @Param("date") LocalDate date);
}