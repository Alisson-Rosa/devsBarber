package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.dto.BarberScheduleDTO;
import project.devsbarber.model.dto.ScheduleDTO;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.enums.EnumDays;
import project.devsbarber.model.services.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class BarberController {

    @Autowired private UserService userService;
    @Autowired private BarberService barberService;
    @Autowired private TimeKeyService timeKeyService;
    @Autowired private TimetableBarbersService timetableBarbersService;
    @Autowired private ScheduleService scheduleService;
    @Autowired private TimetableBarbersService timetableBarberService;

    @RequestMapping(value = "/barbers")
    public String barberIndex(){
        return "redirect:/barbers/1";
    }

    @RequestMapping(value = "/barbers/{pageId}")
    public String barbers(final Model model, @PathVariable int pageId) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        Page<Barber> barbersPagination = barberService.paginationBarber(PageRequest.of(pageId - 1, 15, Sort.Direction.DESC, "name"));
        List<Barber> barberList = barbersPagination.getContent();
        long countBarberTotal = barbersPagination.getTotalElements();
        int totalPages = barbersPagination.getTotalPages();
        int countBarberPage = barbersPagination.getNumberOfElements();
        int currentPage = barbersPagination.getNumber() + 1;
        int previousPage = currentPage - 1;
        int nextPage = currentPage + 1;
        int finalPage = totalPages;

        model.addAttribute("barberList", barberList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("countBarberTotal", countBarberTotal);
        model.addAttribute("countBarberPage", countBarberPage);
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
            throw new Exception("Nome de usuario j?? cadastrado"); //TODO Alterar para mensagem na tela
        }
        barberRegister.setEnable(true);
        Barber barber = barberService.saveOrUpdate(barberRegister);
        timetableBarbersService.saveTimetableBarber(barber);
        return "redirect:/barbers/barberRegister/" + barberRegister.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "barbers/barberRegister/edit/{barberId}")
    public String barberEditResult(@ModelAttribute Barber barberEdit, @PathVariable("barberId") Long id){
        barberEdit.setId(id);
        barberService.saveOrUpdate(barberEdit);
        timetableBarbersService.saveTimetableBarber(barberEdit);
        return "redirect:/barbers/barberRegister/" + barberEdit.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "barbers/delete/{barberId}")
    public String deleteBarber(@PathVariable("barberId") Long id) {

        barberService.deleteByid(id);

        return "redirect:/barbers";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/barbersTimeTables")
    public String barbersTimeTables(final Model model, ScheduleDTO scheduleDTO) {

        User user = userService.getUserLogado();
        model.addAttribute("userLogado",user);

        LocalDate date = scheduleDTO.getDate();
        if(date == null){
            scheduleDTO.setDate(LocalDate.now());
        }

        model.addAttribute("scheduleDTO", scheduleDTO);
        List<TimetableBarbers> barberScheduleList = new ArrayList<>();
        List<Barber> barberAll = barberService.findAll();
        for (Barber barber : barberAll) {
            EnumDays dayOff = barber.getDayOff();
            DayOfWeek dayOfWeek = scheduleDTO.getDate().getDayOfWeek();
            if(dayOfWeek.equals(dayOff.getDayOfWeek())){
                TimetableBarbers timetableBarber = new TimetableBarbers();
                timetableBarber.setBarber(barber);
                barberScheduleList.add(timetableBarber);
                continue;
            }

            List<Schedule> scheduleList = scheduleService.findByBarberAndDate(barber, scheduleDTO.getDate());
            List<Long> unavailableHours = new ArrayList<>();
            for (Schedule schedule : scheduleList) {
                long key = schedule.getTimetableBarber().getTimeKey().getKey();
                unavailableHours.add(key);
            }
            List<TimeKey> timeKeyUnavailable = timeKeyService.findByKey(unavailableHours);
            List<TimetableBarbers> barberNotInTimeKeyList = timetableBarberService.findByBarberNotInTimeKey(barber, timeKeyUnavailable);
            barberScheduleList.addAll(barberNotInTimeKeyList);
        }

        List<BarberScheduleDTO> barberScheduleDTOs = scheduleService.getBarberScheduleDTO(barberScheduleList);
        Collections.sort(barberScheduleDTOs);
        model.addAttribute("barberScheduleList", barberScheduleDTOs);

        Long initialBusinessHours = 25L; //TODO Alterar para parametro da tela de configs
        Long finalBusinessHours = 85L;
        List<TimeKey> hours = timeKeyService.findByInitialAndFinalKeys(initialBusinessHours, finalBusinessHours);
        model.addAttribute("hours", hours);

        return "barbersTimeTables"; //TODO criar um atributo cliente pra preenchar quando o usuario for admin
    }

    @RequestMapping(method = RequestMethod.POST, value = "/timeTablesSearch")
    public String timeTablesSearch (@ModelAttribute ScheduleDTO scheduleDTO, final Model model){
        return barbersTimeTables(model, scheduleDTO);
    }
}
