package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Schedule;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends CrudRepository <Schedule, Long> {
    List<Schedule> findByBarber(long barberId);
}