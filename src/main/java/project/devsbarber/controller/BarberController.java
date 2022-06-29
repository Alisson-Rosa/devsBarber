package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(method = RequestMethod.GET, value = "barbers/barberRegister/{barberId}")
    public String barberEdit(final Model model, @PathVariable("barberId") Long id) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        Barber barberRegister = barberService.get(id);
        model.addAttribute("barberRegister", barberRegister);

        EnumDays[] enumDays = EnumDays.enumDaysAll();
        model.addAttribute("enumDays", enumDays);

        EnumDays dayOfWeekBarber = EnumDays.getEnumToDayOfWeek(barberRegister.getDayOff());
        model.addAttribute("dayOfWeekBarber", dayOfWeekBarber);

        List<TimeKey> timeKeyList = timeKeyService.findAll();
        model.addAttribute("timeKeyList", timeKeyList);

        return "barberEdit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createBarber")
    public String createBarber(@ModelAttribute Barber barberRegister) throws Exception {
        String name = barberRegister.getName();
        boolean existUsername = barberService.existUsername(name);//TODO Alterar para findByUsername
        if(existUsername){
            throw new Exception("Nome de usuario já cadastrado"); //TODO Alterar para mensagem na tela
        }
        barberService.saveOrUpdate(barberRegister);
        return "redirect:/barbers/barberRegister/" + barberRegister.getId();
    }
}
