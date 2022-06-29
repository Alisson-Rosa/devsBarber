package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.repository.RoleRepository;
import project.devsbarber.repository.UserRepository;

@Controller
public class UserFormController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/userForm")
    public String usersForm(final Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userFormRegister")
    public String result(@ModelAttribute User user) {
        Role userRole = roleRepository.getByName("USER");
        user.setRole(userRole);
        userRepository.save(user);
        return "redirect:/userForm";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loginVoltar")
    public String Voltar() {
        return "login";
    }
}