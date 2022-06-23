package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.repository.BarberRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarberService {

    @Autowired
    BarberRepository barberRepository;

    public List<Barber> findAllAvailable(LocalDate date) {
        List<Barber> barberListAll = (List<Barber>) barberRepository.findAll();

        List<Barber> barberList = new ArrayList<>();
        for (Barber barber : barberListAll) {
            DayOfWeek dayOff = barber.getDayOff();
            DayOfWeek dateSchedule = date.getDayOfWeek();
            if(!dayOff.equals(dateSchedule)){
                barberList.add(barber);
            }
        }

        return barberList;
    }
}