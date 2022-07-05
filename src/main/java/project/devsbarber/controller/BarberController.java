package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.Barber;
import project.devsbarber.model.entities.TimeKey;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.enums.EnumDays;
import project.devsbarber.model.services.BarberService;
import project.devsbarber.model.services.TimeKeyService;
import project.devsbarber.model.services.TimetableBarbersService;
import project.devsbarber.model.services.UserService;
import project.devsbarber.model.repository.RoleRepository;
import project.devsbarber.util.UtilProject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BarberController {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserService userService;
    @Autowired private BarberService barberService;
    @Autowired private TimeKeyService timeKeyService;
    @Autowired private TimetableBarbersService timetableBarbersService;

    @RequestMapping(value = "/barbers")
    public String usersIndex(){
        return "redirect:/barbers/1";
    }

    @RequestMapping(value = "/barbers/{pageId}")
    public String users(final Model model, @PathVariable Long pageId) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        User userRegister = new User();
        model.addAttribute("userRegister", userRegister);

        List<Barber> barberList = barberService.findAll(); //TODO alterar query com limit de 30 / agrupar para fazer paginação
        model.addAttribute("barberList", barberList);

        long countBarbers = barberService.countBarbers();
        model.addAttribute("countBarber", countBarbers);

        List<Long> countPaginasList = UtilProject.countPaginasList(countBarbers);
        model.addAttribute("countPaginasList", countPaginasList);

        Long currentPage = pageId;
        Long previousPage = pageId - 1;
        Long nextPage = pageId + 1;
        int finalPage = countPaginasList.size();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("finalPage", finalPage);

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

        List<LocalTime> lunchDurationList = new ArrayList<>();
        LocalTime time = LocalTime.of(0,0);
        for(int i=0; i<10; i++){
            time = time.plusMinutes(15);
            lunchDurationList.add(time);
        }
        model.addAttribute("lunchDurationList", lunchDurationList);

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

        List<TimeKey> timeKeyList = timeKeyService.findAll();
        model.addAttribute("timeKeyList", timeKeyList);

        List<LocalTime> lunchDurationList = new ArrayList<>();
        LocalTime time = LocalTime.of(0,0);
        for(int i=0; i<10; i++){
            time = time.plusMinutes(15);
            lunchDurationList.add(time);
        }
        model.addAttribute("lunchDurationList", lunchDurationList);

        return "barberEdit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "barbers/barberRegister/createBarber")
    public String createBarber(@ModelAttribute Barber barberRegister) throws Exception {
        String name = barberRegister.getName();
        boolean existUsername = barberService.existUsername(name);
        if(existUsername){
            throw new Exception("Nome de usuario já cadastrado"); //TODO Alterar para mensagem na tela
        }
        Barber barber = barberService.saveOrUpdate(barberRegister);
        timetableBarbersService.saveTimetableBarber(barber);
        return "redirect:/barbers/barberRegister/" + barberRegister.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "barbers/barberRegister/edit/{barberId}")
    public String barberEditResult(@ModelAttribute Barber barberEdit, @PathVariable("barberId") Long id){
        barberEdit.setId(id);
        barberService.saveOrUpdate(barberEdit);
        return "redirect:/barbers/barberRegister/" + barberEdit.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "barbers/delete/{barberId}")
    public String deleteBarber(@PathVariable("barberId") Long id) {

        try{
            barberService.deleteByid(id);
        } catch (Exception e){
            throw e; //TODO alterar para mensagem na tela
        }

        return "redirect:/barbers";
    }
}
