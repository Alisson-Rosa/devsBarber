package project.devsbarber.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.Schedule;
import project.devsbarber.model.entities.TimetableBarbers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByBarber(Barber barberId);

    @Query(value = "SELECT s FROM Schedule s WHERE s.barber = :barber and s.date = :date")
    List<Schedule> findByBarberAndDate(@Param("barber") Barber barber, @Param("date") LocalDate date);

    @Query(value = "SELECT s FROM Schedule s where s.id in :ids order by s.date")
    Page<Schedule> findSchedulesByIdsWhitPagination(@Param("ids") Collection<Long> ids, Pageable pageable);

    @Query(value = "SELECT s FROM Schedule s where s.date = :date order by s.date")
    List<Schedule> findByDate(@Param("date") LocalDate date);

    @Query(value = "SELECT s FROM Schedule s where s.id in :ids")
    List<Schedule> findByIds(@Param("ids") List<Long> ids);
}