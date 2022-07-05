package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.dto.BarberScheduleDTO;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private BarberService barberService;
    @Autowired private TimetableBarbersService timetableBarberService;

    public List<Long> findUnavailableHours(Barber barber, Cut cut, LocalDate localDate){ //pega os horários disponíveis

        long barberId = barber.getId();

        Barber barber1 = barberService.get(1L);
        List<Schedule> scheduleList = scheduleRepository.findByBarber(barber1);//TODO alterar para barbeiro que recebemos por parâmetro
        if(scheduleList != null && !scheduleList.isEmpty()){
            Map<Long, List<Long>> map = new HashMap<>();

            for (Schedule schedule : scheduleList) { //Colocar horários em um map de id chave sendo o corte e valores os pedaços de tempo

                Cut cutClient = schedule.getCut();
                Long cutId = cutClient.getId();
                TimeKey timeKey = schedule.getTimetableBarber().getTimeKey();
                long key = timeKey.getKey();

                if(!map.containsKey(cutId)){
                    List<Long> list = new ArrayList<>();
                    list.add(key);
                    map.put(cutId, list);
                } else {
                    List<Long> list = map.get(cutId);
                    list.add(key);
                    map.replace(cutId, list);
                }

            }

            Set<Long> cuts = map.keySet();
            List<TimeKeyCutVo> vos = new ArrayList<>();
            for (Long cutId : cuts) {
                TimeKeyCutVo vo = new TimeKeyCutVo();
                vo.setIdCutId(cutId);
                vo.setTimeKeys(map.get(cutId));

                vos.add(vo);
            }

            Integer sizeCut = cut.getSize();
            List<Long> impossibleStartTimes = new ArrayList<>();
            List<Long> unavailableHours = new ArrayList<>();
            for (TimeKeyCutVo voFinal : vos) {
                unavailableHours = voFinal.getTimeKeys();
                long firstTime = unavailableHours.get(0);

                impossibleStartTimes = validateStartValues(sizeCut, firstTime);
                unavailableHours.addAll(impossibleStartTimes);
            }
            return unavailableHours;
        } else {
            return null;
        }
    }

    private static List<Long> validateStartValues(Integer tamanhoCorte, Long first) {
        List <Long> list = new ArrayList<>();

        for(int i=0; i<tamanhoCorte-1; i++){
            first -= 1;
            list.add(first);
        }

        return list;
    }

    public List<BarberScheduleDTO> getBarberScheduleDTO(List<TimetableBarbers> barberScheduleList) {

        Map<Barber, List<TimeKey>> mapDto = new HashMap<>();
        for (TimetableBarbers timetableBarbers : barberScheduleList) {
            Barber barber = timetableBarbers.getBarber();
            TimeKey timeKey = timetableBarbers.getTimeKey();
            if(!mapDto.containsKey(barber)){
                List<TimeKey> timeKeyList = new ArrayList<>();
                timeKeyList.add(timeKey);
                mapDto.put(barber, timeKeyList);
            } else {
                List<TimeKey> timeKeyList = mapDto.get(barber);
                timeKeyList.add(timeKey);
                mapDto.replace(barber, timeKeyList);
            }
        }

        List<BarberScheduleDTO> dtoList = new ArrayList<>();
        for (Barber barber : mapDto.keySet()) {
            List<TimeKey> timeKeyList = mapDto.get(barber);

            BarberScheduleDTO dto = new BarberScheduleDTO();
            dto.setBarber(barber);
            dto.setTimeKeyList(timeKeyList);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional(rollbackFor=Exception.class)
    public Schedule saveSchedule(User client, Cut cut, LocalDate date, Long barberId, Long keyHours) {
        Barber barber = barberService.get(barberId);

        Integer size = cut.getSize();
        for(int i=1; i<=size ; i++){
            TimetableBarbers timetable = timetableBarberService.getByTimeKeyId(keyHours);

            Schedule schedule = new Schedule();
            schedule.setClient(client);
            schedule.setCut(cut);
            schedule.setDate(date);
            schedule.setBarber(barber);
            schedule.setTimetableBarber(timetable);
            scheduleRepository.save(schedule);
            keyHours += 1;
        }

        return null;
    }
}

class TimeKeyCutVo {
    Long cudtId;
    List<Long> timeKeys;

    public Long getCudtId() {
        return cudtId;
    }

    public void setIdCutId(Long cutId) {
        this.cudtId = cutId;
    }

    public List<Long> getTimeKeys() {
        return timeKeys;
    }

    public void setTimeKeys(List<Long> timeKeys) {
        this.timeKeys = timeKeys;
    }
}