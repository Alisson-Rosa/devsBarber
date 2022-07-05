package project.devsbarber.model.repository;

import org.hibernate.annotations.SortComparator;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.entities.TimetableBarbers;

import java.util.List;

@Repository
@Transactional
public interface TimetableBarbersRepository extends JpaRepository<TimetableBarbers, Long> {

    TimetableBarbers getById(Long id);

    @Query(value = "SELECT * FROM timetable_barbers tb  where tb.BARBER_ID = :barber and tb.TIME_KEY_ID not in :unavailableHours" +
            " order by tb.BARBER_ID desc, tb.TIME_KEY_ID desc",
            nativeQuery = true)
    List<TimetableBarbers> findByBarberNotInTimeKeyOrderByBarberDescAndTimeKeyDesc(@Param("barber") Barber barber,
                                                                                   @Param("unavailableHours") List<TimeKey> unavailableHours);

    @Query(value = "SELECT * FROM timetable_barbers tb WHERE tb.TIME_KEY_ID = :keyId",
            nativeQuery = true)
    TimetableBarbers getByTimeKeyId(@Param("keyId") Long keyId);
}
