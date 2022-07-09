package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.devsbarber.model.dto.TimeKeyStringDTO;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.repository.TimeKeyRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeKeyService {

    @Autowired TimeKeyRepository timeKeyRepository;

    public List<TimeKey> findAll(){
        return (List<TimeKey>) timeKeyRepository.findAll();
    }

    public List<TimeKeyStringDTO> convertString(List<TimeKey> timeKeyList) {
        List<TimeKeyStringDTO> listVo = new ArrayList<>();
        for (TimeKey timeKey : timeKeyList) {
            long id = timeKey.getKey();
            LocalTime time = timeKey.getTime();
            String timeString = time.toString();
            TimeKeyStringDTO vo = new TimeKeyStringDTO();
            vo.setId(id);
            vo.setTime(time);
            vo.setTimeString(timeString);
        }

        return listVo;
    }

    public List<TimeKey> findByKey(List<Long> unavailableHours) {
        return (List<TimeKey>) timeKeyRepository.findAllById(unavailableHours);
    }

    public List<TimeKey> findByInitialAndFinalKeys(Long initHour, Long finalHours) {
        return timeKeyRepository.findByInitialAndFinalKeys(initHour, finalHours);
    }

    public List<TimeKey> findByInitialAndFinalTimes(LocalTime initHour, LocalTime finalHours) {
        return timeKeyRepository.findByInitialAndFinalTime(initHour, finalHours);
    }

    public TimeKey getByTime(LocalTime workEndTime) {
        return timeKeyRepository.getByTime(workEndTime);
    }
}

