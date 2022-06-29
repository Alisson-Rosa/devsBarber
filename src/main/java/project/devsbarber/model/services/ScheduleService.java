package project.devsbarber.model.services;

import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.Cut;
import project.devsbarber.model.entities.Schedule;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.repository.ScheduleRepository;

import java.util.*;

@Service
public class ScheduleService {

    ScheduleRepository scheduleRepository;

    public List<Long> findMapByFiltro(Barber barber, Cut cut){ //pegar os horários disponíveis

        long barberId = barber.getId();

        List<Schedule> scheduleList = scheduleRepository.findByBarber(barberId);
        Map<Long, List<Long>> map = new HashMap<>();

        for (Schedule schedule : scheduleList) { //Colocar horários em um map de id chave sendo o corte e valores os pedaços de tempo

            Cut cutClient = schedule.getCut();
            Long cutId = cutClient.getId();
            TimeKey timeKey = schedule.getTimeKey();
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
        List<Long> unavailableHours = new ArrayList<>();
        for (TimeKeyCutVo voFinal : vos) {
            List<Long> timeKeys = voFinal.getTimeKeys();
            long firstTime = timeKeys.get(0);

            unavailableHours = validateStartValues(sizeCut, firstTime);
            timeKeys.addAll(unavailableHours);
        }

        return unavailableHours;
    }

    private static List<Long> validateStartValues(Integer tamanhoCorte, Long first) {
        List <Long> list = new ArrayList<>();

        for(int i=0; i<tamanhoCorte-1; i++){
            first -= 1;
            list.add(first);
        }

        return list;
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