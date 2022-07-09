package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.entities.TimetableBarbers;
import project.devsbarber.model.repository.TimetableBarbersRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimetableBarbersService {

    @Autowired private TimetableBarbersRepository timetableBarbersRepository;
    @Autowired private TimeKeyService timeKeyService;

    public TimetableBarbers saveOrUpdate(TimetableBarbers timetableBarbers){
        return timetableBarbersRepository.save(timetableBarbers);
    }

    public List<TimetableBarbers> findByBarberNotInTimeKey(Barber barber, List<TimeKey> unavailableHours) {
        if(unavailableHours.isEmpty()){
            return timetableBarbersRepository.findByBarberOrderByBarberDescAndTimeKeyDesc(barber);
        }

        return timetableBarbersRepository.findByBarberNotInTimeKeyOrderByBarberDescAndTimeKeyDesc(barber, unavailableHours);
    }

    public TimetableBarbers getByTimeKeyId(Long keyHours, Long barberId) {
        return timetableBarbersRepository.getByTimeKeyId(keyHours, barberId);
    }

    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRES_NEW)
    public void saveTimetableBarber(Barber barber) {

        LocalTime lunchTime = barber.getLunchTime();
        LocalTime lunchDuration = barber.getLunchDuration();
        LocalTime workEndTime = barber.getWorkEndTime();
        LocalTime workStartTime = barber.getWorkStartTime();

        List<TimeKey> workTimeList = timeKeyService.findByInitialAndFinalTimes(workStartTime, workEndTime.minusMinutes(15));

        LocalTime finalLunch = null;
        finalLunch = lunchTime.plusHours(lunchDuration.getHour()).plusMinutes(lunchDuration.getMinute());
        for (TimeKey timeKey : workTimeList) {
            LocalTime time = timeKey.getTime();
            if(time.equals(lunchTime) || time.isAfter(lunchTime)){
                if(!time.isBefore(finalLunch)){
                    TimetableBarbers timetableBarbers = new TimetableBarbers();
                    timetableBarbers.setBarber(barber);
                    timetableBarbers.setTimeKey(timeKey);

                    saveOrUpdate(timetableBarbers);
                }
                continue;
            }

            TimetableBarbers timetableBarbers = new TimetableBarbers();
            timetableBarbers.setBarber(barber);
            timetableBarbers.setTimeKey(timeKey);
            saveOrUpdate(timetableBarbers);
        }
    }
}
