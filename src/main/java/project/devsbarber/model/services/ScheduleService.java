package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.dto.BarberScheduleDTO;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private BarberService barberService;
    @Autowired private TimetableBarbersService timetableBarberService;
    @Autowired private TimeKeyService timeKeyService;

    public List<Long> findUnavailableHours(Barber barber, Cut cut, LocalDate localDate){ //pega os horários disponíveis

        List<Schedule> scheduleList = scheduleRepository.findByBarberAndDate(barber, localDate);
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
                vo.setCutId(cutId);
                vo.setTimeKeys(map.get(cutId));

                vos.add(vo);
            }

            vos.addAll(addLunchTimeAndWorkEndTime(barber));

            Integer sizeCut = cut.getSize();
            List<Long> impossibleStartTimes = new ArrayList<>();
            List<Long> unavailableHours = new ArrayList<>();
            for (TimeKeyCutVo voFinal : vos) {
                unavailableHours.addAll(voFinal.getTimeKeys());
                long firstTime = 0;
                if(voFinal.getCudtId() == 999L){
                    List<Long> timeKeys = voFinal.getTimeKeys();
                    firstTime = timeKeys.get(0);
                } else {
                    firstTime = unavailableHours.get(0);
                }

                impossibleStartTimes = validateStartValues(sizeCut, firstTime);
                unavailableHours.addAll(impossibleStartTimes);
            }
            return unavailableHours;
        } else {
            List<TimeKeyCutVo> timeKeyCutVos = addLunchTimeAndWorkEndTime(barber);
            List<Long> unavailableHours = new ArrayList<>();
            for (TimeKeyCutVo vo : timeKeyCutVos) {
                Integer sizeCut = cut.getSize();
                long firstTime = vo.getTimeKeys().get(0);

                unavailableHours = validateStartValues(sizeCut, firstTime);
            }

            return unavailableHours;
        }
    }

    private List<TimeKeyCutVo> addLunchTimeAndWorkEndTime(Barber barber) {
        List<TimeKeyCutVo> vos = new ArrayList<>();

        LocalTime workEndTime = barber.getWorkEndTime();
        TimeKey workEndTimeKey = timeKeyService.getByTime(workEndTime); //para adicionar o horário final de expediente como parametro
        TimeKeyCutVo vo1 = new TimeKeyCutVo();
        vo1.setCutId(999L);
        List<Long> listEndTimeKey = new ArrayList<>(); //Necessário deixar a lista fora do set para não estourar UnsupportedOperationException
        listEndTimeKey.add(workEndTimeKey.getKey());
        vo1.setTimeKeys(listEndTimeKey);
        vos.add(vo1);

        LocalTime lunchTime = barber.getLunchTime();
        TimeKey lunchTimeKey = timeKeyService.getByTime(lunchTime); //para adicionar o horário final de expediente como parametro
        TimeKeyCutVo vo2 = new TimeKeyCutVo();
        vo2.setCutId(999L);
        List<Long> listLunchTimeKey = new ArrayList<>(); //Necessário deixar a lista fora do set para não estourar UnsupportedOperationException
        listLunchTimeKey.add(lunchTimeKey.getKey());
        vo2.setTimeKeys(listLunchTimeKey);
        vos.add(vo2);

        return vos;
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
        TimeKey timeKey = timeKeyService.getByKey(keyHours);

        Integer size = cut.getSize();
        for(int i=1; i<=size ; i++){
            TimetableBarbers timetable = timetableBarberService.getByTimeKeyId(keyHours, barberId); //TODO retornando dois valores (Passar barbeiro como parametro)

            Schedule schedule = new Schedule();
            schedule.setClient(client);
            schedule.setCut(cut);
            schedule.setDate(date);
            schedule.setBarber(barber);
            schedule.setTimetableBarber(timetable);
            schedule.setTime(timeKey.getTime());
            scheduleRepository.save(schedule);
            keyHours += 1;
        }

        return null;
    }

    public List<Schedule> findByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }

    public List<Schedule> findByBarberAndDate(Barber barber, LocalDate date) {
        return scheduleRepository.findByBarberAndDate(barber, date);
    }
}

class TimeKeyCutVo {
    private Long cudtId;
    private List<Long> timeKeys;

    public Long getCudtId() {
        return cudtId;
    }

    public void setCutId(Long cutId) {
        this.cudtId = cutId;
    }

    public List<Long> getTimeKeys() {
        return timeKeys;
    }

    public void setTimeKeys(List<Long> timeKeys) {
        this.timeKeys = timeKeys;
    }
}