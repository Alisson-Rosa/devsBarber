package project.devsbarber.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.enums.EnumDays;
import project.devsbarber.repository.BarberRepository;
import project.devsbarber.repository.RoleRepository;
import project.devsbarber.repository.TimeKeyRepository;
import project.devsbarber.repository.UserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired TimeKeyRepository timeKeyRepository;
    @Autowired BarberRepository barberRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("CLIENT"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.getByName("ADMIN");
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

        LocalTime localTime = LocalTime.of(6,0,0);
        for (int i=25; i<=96; i++){ //começando a partir das 6:00
            TimeKey timeKey = new TimeKey();
            timeKey.setKey(i);
            timeKey.setTime(localTime);

            timeKeyRepository.save(timeKey);
            localTime = localTime.plusMinutes(15L);
        }


        Barber barber = new Barber();
        barber.setName("ALISSON ROSA");
        barber.setDayOff(EnumDays.SEGUNDA);

        LocalTime hoursStart = LocalTime.of(8, 0, 0);
        LocalTime hoursEnd = LocalTime.of(19, 0, 0);
        LocalTime hoursLunch = LocalTime.of(12, 0, 0);
        LocalTime hoursDuration = LocalTime.of(1, 0, 0);

        barber.setWorkStartTime(hoursStart);
        barber.setWorkEndTime(hoursEnd);
        barber.setLunchTime(hoursLunch);
        barber.setLunchDuration(hoursDuration);

        barberRepository.save(barber);
    }
}