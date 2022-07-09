package project.devsbarber.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.enums.EnumDays;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.repository.*;
import project.devsbarber.model.services.TimetableBarbersService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired TimeKeyRepository timeKeyRepository;
    @Autowired BarberRepository barberRepository;
    @Autowired CutRepository cutRepository;

    @Autowired TimetableBarbersService timetableBarbersService;
    @Autowired private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("CLIENT"));
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("GERENTE"));

        Role adminRole = roleRepository.getByName("ADMIN");
        Role gerente = roleRepository.getByName("GERENTE");
        Role clientRole = roleRepository.getByName("CLIENT");

        LocalDate date = LocalDate.now();
        User user = new User("admin@code.com", passwordEncoder.encode("password"),
                "Admin", true, "admin", date, "(41) 99741-5901");
        user.setRole(adminRole);
        userRepository.save(user);

        user = new User("user@code.com", passwordEncoder.encode("password"),
                "User", true, "user", date, "(41) 99741-5901");
        user.setRole(clientRole);
        userRepository.save(user);

        user = new User("Teste@code.com", passwordEncoder.encode("password"),
                "Teste", true, "TESTE", date, "(41) 99741-5901");
        user.setRole(gerente);
        userRepository.save(user);

        LocalTime localTime = LocalTime.of(6,0,0);
        List<TimeKey> timeKeyList = new ArrayList<>();
        for (int i=25; i<=96; i++){ //começando a partir das 6:00
            TimeKey timeKey = new TimeKey();
            timeKey.setKey(i);
            timeKey.setTime(localTime);

            timeKeyRepository.save(timeKey);
            localTime = localTime.plusMinutes(15L);

            timeKeyList.add(timeKey);
        }


        Barber barber = new Barber();
        barber.setName("ALISSON ROSA");
        barber.setDayOff(EnumDays.SEGUNDA);

        LocalTime hoursStart = LocalTime.of(9, 0, 0);
        LocalTime hoursEnd = LocalTime.of(18, 0, 0);
        LocalTime hoursLunch = LocalTime.of(12, 0, 0);
        LocalTime hoursDuration = LocalTime.of(1, 0, 0);

        barber.setWorkStartTime(hoursStart);
        barber.setWorkEndTime(hoursEnd);
        barber.setLunchTime(hoursLunch);
        barber.setLunchDuration(hoursDuration);
        barber.setEnable(true);

        barberRepository.save(barber);

        Cut cut = new Cut();
        cut.setValue(30.00);
        cut.setTypeCut(TypeCut.BARBA);
        cut.setTime(LocalTime.of(0,30,0));
        cut.setSize(2);

        cutRepository.save(cut);
    }
}