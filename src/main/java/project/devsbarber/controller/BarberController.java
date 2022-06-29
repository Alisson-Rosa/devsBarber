package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.enums.EnumDays;
import project.devsbarber.model.services.BarberService;
import project.devsbarber.model.services.TimeKeyService;
import project.devsbarber.model.dto.TimeKeyStringDTO;
import project.devsbarber.model.services.UserService;
import project.devsbarber.repository.RoleRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BarberController {

    @Autowired RoleRepository roleRepository;
    @Autowired UserService userService;
    @Autowired BarberService barberService;
    @Autowired
    TimeKeyService timeKeyService;

    @RequestMapping(value = "/barbers")
    public String users(final Model model) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        User userRegister = new User();
        model.addAttribute("userRegister", userRegister);

        List<Role> roleList = (List<Role>) roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        return "barbers";
    }

    @RequestMapping(value = "/barbers/barberRegister")
    public String barberRegister(final Model model) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        Barber barberRegister = new Barber();
        model.addAttribute("barberRegister", barberRegister);

        EnumDays[] enumDays = EnumDays.enumDaysAll();
        model.addAttribute("enumDays", enumDays);

        List<TimeKey> timeKeyList = timeKeyService.findAll();
        model.addAttribute("timeKeyList", timeKeyList);

        return "barberRegister";
    }

//    @RequestMapping(method = RequestMethod.GET, value = "users/userRegisters/{userId}")
//    public String usersEdit(final Model model, @PathVariable("userId") Long id) {
//
//        User userLogado = userService.getUserLogado();
//        model.addAttribute("userLogado", userLogado);
//
//        User userRegister = userService.getUser(id);
//        model.addAttribute("userRegister", userRegister);
//
//        List<Role> roleList = (List<Role>) roleRepository.findAll();
//        model.addAttribute("roleList", roleList);
//
//        Role role = new Role();
//        model.addAttribute("roleUser", role);
//
//        return "InternalUserEdit";
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBarber")
    public String createBarber(@ModelAttribute Barber barberRegister) throws Exception {
        String name = barberRegister.getName();
        List<Barber> barberList = barberService.findAll();
        for (Barber barber : barberList) {
            if(barber.getName().equals(name)){
                throw new Exception("Nome de usuario já cadastrado");
            }
        }
        barberService.saveOrUpdate(barberRegister);
        return "redirect:/barbers/barberRegister/" + barberRegister.getId();
    }
}
