package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.services.UserService;
import project.devsbarber.model.repository.RoleRepository;
import project.devsbarber.model.repository.UserRepository;

@Controller
public class UserFormController {

    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/userForm")
    public String usersForm(final Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uerForm/register")
    public String result(@ModelAttribute User user) throws Exception {
        Role userRole = roleRepository.getByName("CLIENT");
        boolean existUsername = userService.existUsername(user.getUsername());
        if(existUsername){
            throw new Exception("Usuario já existe!");
        }
        user.setRole(userRole);
        userRepository.save(user);
        return "redirect:/login";
    }
}