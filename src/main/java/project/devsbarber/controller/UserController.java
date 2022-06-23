package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.repository.RoleRepository;
import project.devsbarber.repository.UserRepository;

import java.util.Arrays;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public String usersForm(final Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public String result(@ModelAttribute User user) {
        Role userRole = roleRepository.getByName("USER");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
        return "userForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/loginVoltar")
    public String Voltar() {
        return "login";
    }
}