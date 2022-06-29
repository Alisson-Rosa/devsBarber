package project.devsbarber.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.entities.User;
import project.devsbarber.repository.RoleRepository;
import project.devsbarber.repository.TimeKeyRepository;
import project.devsbarber.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired TimeKeyRepository timeKeyRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("CLIENT"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.getByName("ADMIN");
        Role clientRole = roleRepository.getByName("CLIENT");

        LocalDate date = LocalDate.now();
        User user = new User("admin@code.com", passwordEncoder.encode("password"),
                "Admin", true, "admin", date);
        user.setRole(adminRole);
        userRepository.save(user);

        user = new User("user@code.com", passwordEncoder.encode("password"),
                "User", true, "user", date);
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
    }
}