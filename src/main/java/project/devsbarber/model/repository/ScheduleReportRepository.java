package project.devsbarber.model.repository;

import org.springframework.stereotype.Repository;
import project.devsbarber.model.entities.Schedule;
import project.devsbarber.model.entities.ScheduleReportFilter;
import project.devsbarber.model.enums.TypeCut;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class ScheduleReportRepository {

    @PersistenceContext
    protected EntityManager manager;

    public List<Schedule> findScheduleReportByFilter(ScheduleReportFilter filter){

        Map<String, Object> params = new HashMap<>();

        String query = "select * from schedule s ";
        query += " inner join Usuario c on c.id = s.client_id";
        query += " inner join Cut ct on ct.id = s.cut_id";

        Long barberId = filter.getBarberId();
        Long clientId = filter.getClientId();
        String clientName = filter.getClientName();
        TypeCut typeCut = filter.getTypeCut();
        LocalDate dateEnd = filter.getDateEnd();
        LocalDate dateInit = filter.getDateInit();
        LocalTime timeEnd = filter.getTimeEnd();
        LocalTime timeInit = filter.getTimeInit();

        query += " where 1=1 ";

        if(dateInit != null){
            query += " and s.date >= :dateInit ";
            params.put("dateInit", dateInit);
        }

        if(dateEnd != null){
            query += " and s.date <= :dateEnd ";
            params.put("dateEnd", dateEnd);
        }

        if(timeInit != null){
            query += " and s.time >= :timeInit ";
            params.put("timeInit", timeInit);
        }

        if(timeEnd != null){
            query += " and s.time <= :timeEnd ";
            params.put("timeEnd", timeEnd);
        }

        if(barberId != null){
            query += " and s.barber_id = :barberId";
            params.put("barberId", barberId);
        }

        if(clientId != null){
            query += " and s.client_id = :clientId";
            params.put("clientId", clientId);
        }

        if(clientName != null && !clientName.equals("")){
            query += (" and lower(c.name) like :clientName ");
            clientName = clientName.toLowerCase(Locale.ROOT);
            params.put("clientName", "%" + clientName + "%");
        }

        if(typeCut != null){
            query += " and ct.typeCut = :typeCut";
            params.put("typeCut", typeCut.ordinal());
        }

        var q = manager.createNativeQuery(query, Schedule.class);

        if(!params.isEmpty()){
            Set<String> strings = params.keySet();
            for (String string : strings) {
                Object object = params.get(string);
                q.setParameter(string, object);
            }
        }

        return q.getResultList();
    }
}
