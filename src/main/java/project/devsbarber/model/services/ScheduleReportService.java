package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.devsbarber.model.dto.CountTotalDTO;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.repository.ScheduleReportRepository;
import project.devsbarber.model.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleReportService {

    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private ScheduleReportRepository scheduleReportRepository;

    public Page<Schedule> paginationScheduleReport(List<Long> ids, PageRequest pageable) {
        return scheduleRepository.findSchedulesByIdsWhitPagination(ids, pageable);
    }

    public List<Long> agroupSchedulesIds(List<Schedule> scheduleList) {

        List<Long> ids = new ArrayList<>();
        Map<Barber, List<Map<LocalDate, LocalTime>>> mapGroup = new HashMap<>();
        for (Schedule schedule : scheduleList) {
            if(!mapGroup.containsKey(schedule.getBarber())){
                List<Map<LocalDate, LocalTime>> listMap = new ArrayList<>();
                Map<LocalDate, LocalTime> map = new HashMap<>();
                LocalDate date = schedule.getDate();
                LocalTime time = schedule.getTime();
                map.put(date, time);
                listMap.add(map);

                Barber barber = schedule.getBarber();
                mapGroup.put(barber, listMap);
                if(!ids.contains(schedule.getId())){
                    ids.add(schedule.getId());
                }
            } else {
                Barber barber = schedule.getBarber();
                Map<LocalDate, LocalTime> map = new HashMap<>();
                LocalDate date = schedule.getDate();
                LocalTime time = schedule.getTime();
                map.put(date, time);
                List<Map<LocalDate, LocalTime>> maps = mapGroup.get(barber);
                if(!maps.contains(map)){
                    maps.add(map);
                    mapGroup.replace(barber, maps);

                    if(!ids.contains(schedule.getId())){
                        ids.add(schedule.getId());
                    }
                }
            }
        }

        return ids;
    }

    public List<Schedule> findByFilter(ScheduleReportFilter filter) {
        List<Schedule> scheduleList = new ArrayList<>();
        if(filter == null){
            return scheduleList = scheduleRepository.findAll();
        }

        return scheduleList = scheduleReportRepository.findScheduleReportByFilter(filter);
    }

    public CountTotalDTO countTotais(List<Schedule> scheduleList) {

        List<Barber> barberList = new ArrayList<>();
        List<LocalDate> dateList = new ArrayList<>();
        List<LocalTime> timeList = new ArrayList<>();
        List<User> clientList = new ArrayList<>();
        List<TypeCut> typeCutsList = new ArrayList<>();
        Double totalValue = 0.0;
        for (Schedule schedule : scheduleList) {
            Barber barber = schedule.getBarber();
            if(!barberList.contains(barber)){
                barberList.add(barber);
            }

            LocalDate date = schedule.getDate();
            if(!dateList.contains(date)){
                dateList.add(date);
            }

            LocalTime time = schedule.getTime();
            if(!timeList.contains(time)){
                timeList.add(time);
            }

            User client = schedule.getClient();
            if(!clientList.contains(client)){
                clientList.add(client);
            }

            TypeCut typeCut = schedule.getCut().getTypeCut();
            if(!typeCutsList.contains(typeCut)){
                typeCutsList.add(typeCut);
            }

            Double value = schedule.getCut().getValue();
            totalValue += value;
        }

        CountTotalDTO totalDTO = new CountTotalDTO();
        totalDTO.setTotalDates(dateList);
        totalDTO.setTotalClients(clientList);
        totalDTO.setTotalBarbers(barberList);
        totalDTO.setTotalTypeCuts(typeCutsList);
        totalDTO.setTotalValue(totalValue);

        return totalDTO;
    }

    public List<Schedule> findByIds(List<Long> ids) {
        return scheduleRepository.findByIds(ids);
    }
}
