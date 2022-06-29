package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.services.BarberService;
import project.devsbarber.model.services.CutService;
import project.devsbarber.model.services.ScheduleService;
import project.devsbarber.model.services.UserService;
import project.devsbarber.repository.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BarberService barberService;

    @Autowired
    CutService cutService;

    @RequestMapping(method = RequestMethod.GET, value = "/schedule")
    public String agendaForm(final Model model) {

        User user = userService.getUserLogado();
        model.addAttribute("user",user);

        LocalDate localDate = LocalDate.now(); //TODO alterar para data selecionada pelo usuario
        List<Barber> barberList = barberService.findAllAvailable(localDate);
        model.addAttribute("barberList", barberList);

        List <Cut> cutList = cutService.findAll();
        model.addAttribute("cutList", cutList);

        Barber barber = new Barber();
        Cut cut = new Cut();
        List<Long> unavailableHours = scheduleService.findMapByFiltro(barber, cut); //Usar resultado para filtrar na tabela os horários disponiveis

        Schedule schedule = new Schedule();
        model.addAttribute("schedule", schedule);
        return "schedule";
    }
}