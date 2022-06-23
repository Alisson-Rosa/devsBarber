//package project.devsbarber.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import project.devsbarber.model.entities.Role;
//import project.devsbarber.model.entities.User;
//import project.devsbarber.repository.RoleRepository;
//import project.devsbarber.repository.UserRepository;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        roleRepository.save(new Role("USER"));
//        roleRepository.save(new Role("ADMIN"));
//
//        Role adminRole = roleRepository.getByName("ADMIN");
//        Role userRole = roleRepository.getByName("USER");
//
//        LocalDate date = LocalDate.now();
//        User user = new User("admin@code.com", passwordEncoder.encode("password"),
//                "Admin", true, "admin", date);
//        user.setRoles(Arrays.asList(adminRole));
//        userRepository.save(user);
//
//        user = new User("user@code.com", passwordEncoder.encode("password"),
//                "User", true, "user", date);
//        user.setRoles(Arrays.asList(userRole));
//        userRepository.save(user);
//    }
//}