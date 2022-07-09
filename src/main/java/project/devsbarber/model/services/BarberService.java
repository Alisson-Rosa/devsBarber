package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.repository.BarberRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarberService {

    @Autowired
    BarberRepository barberRepository;

    public List<Barber> findAllAvailable(LocalDate date) {
        List<Barber> barberListAll = barberRepository.findAll();

        List<Barber> barberList = new ArrayList<>();
        for (Barber barber : barberListAll) {
            DayOfWeek dayOff = barber.getDayOff().getDayOfWeek();
            DayOfWeek dateSchedule = date.getDayOfWeek();
            if(!dayOff.equals(dateSchedule)){
                barberList.add(barber);
            }
        }

        return barberList;
    }

    public List<Barber> findAll() {
        return (List<Barber>) barberRepository.findAll();
    }

    @Transactional
    public Barber saveOrUpdate(Barber barberRegister) {
        return barberRepository.save(barberRegister);
    }

    public Barber get(Long id) {
        return barberRepository.getById(id);
    }

    public Barber findByName(String name){
        return barberRepository.findByName(name);
    }

    public boolean existUsername(String name) {
        Barber barber = findByName(name);
        return barber != null;
    }

    public void deleteByid(Long id){
        barberRepository.deleteById(id);
    }

    public long countBarbers() {
        return barberRepository.count();
    }

    public Page<Barber> paginationBarber(Pageable pageable) {
        return barberRepository.findAllBarbersWhitPagination(pageable);
    }
}
