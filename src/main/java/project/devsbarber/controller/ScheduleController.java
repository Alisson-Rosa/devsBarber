package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.dto.BarberScheduleDTO;
import project.devsbarber.model.dto.ScheduleDTO;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.enums.EnumDays;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.services.*;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BarberService barberService;

    @Autowired private CutService cutService;
    @Autowired private TimetableBarbersService timetableBarberService;
    @Autowired private TimeKeyService timeKeyService;
    @Autowired private ScheduleReportService scheduleReportService;

    @RequestMapping(method = RequestMethod.GET, value = "/schedule")
    public String scheduleForm(final Model model) {

        User user = userService.getUserLogado();
        model.addAttribute("user",user);

        List <Cut> cutList = cutService.findAll();
        model.addAttribute("cutList", cutList);

        Cut cutSchedule = new Cut();
        model.addAttribute("cutSchedule", cutSchedule);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        model.addAttribute("scheduleDTO", scheduleDTO);
        return "schedule"; //TODO criar um atributo cliente pra preenchar quando o usuario for admin
    }


    @RequestMapping(method = RequestMethod.POST, value = "/barberSearch")
    public String barberSearch(@ModelAttribute ScheduleDTO scheduleDTO, final BindingResult bindingResult, final Model model) {

        User user = userService.getUserLogado();
        model.addAttribute("user",user);

        Long cutId = scheduleDTO.getCutId();
        Cut cut = cutService.get(cutId);
        LocalDate localDate = scheduleDTO.getDate();
        List<Long> unavailableHours = new ArrayList<>();
        List<TimetableBarbers> barberScheduleList = new ArrayList<>();
        List<Barber> barberList = barberService.findAll();
        for (Barber barber : barberList) {//pega todos horários invalidos dos barbeiros
            EnumDays dayOff = barber.getDayOff();
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if(dayOfWeek.equals(dayOff.getDayOfWeek())){
                TimetableBarbers timetableBarber = new TimetableBarbers();
                timetableBarber.setBarber(barber);
                barberScheduleList.add(timetableBarber);
                continue;
            }

            unavailableHours = scheduleService.findUnavailableHours(barber, cut, localDate); //Usar resultado para filtrar na tabela os horários disponiveis
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

        List <Cut> cutList = cutService.findAll();
        model.addAttribute("cutList", cutList);

        Double value = cut.getValue();
        scheduleDTO.setValue(value);
        String valuString = NumberFormat.getCurrencyInstance().format(value);
        scheduleDTO.setValueString(valuString);
        scheduleDTO.setTimeCut(cut.getTime());
        model.addAttribute("scheduleDTO", scheduleDTO);

        return "schedule";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/barberSaveSchedule")
    public String barberSaveSchedule(@ModelAttribute ScheduleDTO scheduleDTO, final BindingResult bindingResult, final Model model) {

        User user = userService.getUserLogado();
        model.addAttribute("user",user);

        Long cutId = scheduleDTO.getCutId();
        Cut cut = cutService.get(cutId);
        Long keyTime = scheduleDTO.getKey();
        TimeKey timeKey = timeKeyService.getByKey(keyTime);
        Barber barber = barberService.get(scheduleDTO.getBarberId());

        Double value = cut.getValue();
        scheduleDTO.setValue(value);
        String valuString = NumberFormat.getCurrencyInstance().format(value);
        scheduleDTO.setValueString(valuString);
        scheduleDTO.setTimeCut(cut.getTime());
        scheduleDTO.setTipeCut(cut.getTypeCut());
        scheduleDTO.setTimeKey(timeKey.getTime());
        scheduleDTO.setBarberName(barber.getName());
        scheduleDTO.setSave(false);
        model.addAttribute("scheduleDTO", scheduleDTO);

        return "scheduleConfirm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/confirSaveSchedule")
    public String confirmSaveSchedule(@ModelAttribute ScheduleDTO scheduleDTO, final BindingResult bindingResult, final Model model){
        User user = userService.getUserLogado();
        model.addAttribute("user",user);

        if(!scheduleDTO.isConfirm()){
            return barberSearch(scheduleDTO, bindingResult, model);
        }

        Long cutId = scheduleDTO.getCutId();
        Cut cut = cutService.get(cutId);

        Long barberId = scheduleDTO.getBarberId();
        LocalDate date = scheduleDTO.getDate();
        Long keyHours = scheduleDTO.getKey();

        scheduleService.saveSchedule(user, cut, date, barberId, keyHours);

        Long keyTime = scheduleDTO.getKey();
        TimeKey timeKey = timeKeyService.getByKey(keyTime);
        Barber barber = barberService.get(scheduleDTO.getBarberId());

        Double value = cut.getValue();
        scheduleDTO.setValue(value);
        String valuString = NumberFormat.getCurrencyInstance().format(value);
        scheduleDTO.setValueString(valuString);
        scheduleDTO.setTimeCut(cut.getTime());
        scheduleDTO.setTipeCut(cut.getTypeCut());
        scheduleDTO.setTimeKey(timeKey.getTime());
        scheduleDTO.setBarberName(barber.getName());
        scheduleDTO.setSave(true);
        model.addAttribute("scheduleDTO", scheduleDTO);

        return "scheduleConfirm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cancelSaveSchedule")
    public String cancelSaveSchedule(@ModelAttribute ScheduleDTO scheduleDTO, final BindingResult bindingResult, final Model model){

        Long cutId = scheduleDTO.getCutId();
        Cut cut = cutService.get(cutId);
        LocalDate localDate = scheduleDTO.getDate();
        List<Long> unavailableHours = new ArrayList<>();
        List<TimetableBarbers> barberScheduleList = new ArrayList<>();
        List<Barber> barberList = barberService.findAll();
        for (Barber barber : barberList) {//pega todos horários invalidos dos barbeiros
            EnumDays dayOff = barber.getDayOff();
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if(dayOfWeek.equals(dayOff.getDayOfWeek())){
                TimetableBarbers timetableBarber = new TimetableBarbers();
                timetableBarber.setBarber(barber);
                barberScheduleList.add(timetableBarber);
                continue;
            }

            unavailableHours = scheduleService.findUnavailableHours(barber, cut, localDate); //Usar resultado para filtrar na tabela os horários disponiveis
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

        List <Cut> cutList = cutService.findAll();
        model.addAttribute("cutList", cutList);

        Double value = cut.getValue();
        scheduleDTO.setValue(value);
        String valuString = NumberFormat.getCurrencyInstance().format(value);
        scheduleDTO.setValueString(valuString);
        scheduleDTO.setTimeCut(cut.getTime());
        model.addAttribute("scheduleDTO", scheduleDTO);

        return "schedule";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mySchedules")
    public String mySchedules(final Model model){
        return mySchedules(model, 1);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mySchedules/{pageId}")
    public String mySchedules(final Model model, @PathVariable int pageId){
        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);


        ScheduleReportFilter filter = new ScheduleReportFilter();
        filter.setClientId(userLogado.getId());

        List<Schedule> scheduleListByFilter = scheduleReportService.findByFilter(filter);

        List<Long> ids = scheduleReportService.agroupSchedulesIds(scheduleListByFilter);
        Page<Schedule> scheduleReportPagination = scheduleReportService.paginationScheduleReport(ids, PageRequest.of(pageId - 1, 15, Sort.Direction.DESC, "date"));
        List<Schedule> scheduleList = scheduleReportPagination.getContent();
        long countScheduleTotal = scheduleReportPagination.getTotalElements();
        int totalPages = scheduleReportPagination.getTotalPages();
        int countSchedulePage = scheduleReportPagination.getNumberOfElements();
        int currentPage = scheduleReportPagination.getNumber() + 1;
        int previousPage = currentPage - 1;
        int nextPage = currentPage + 1;
        int finalPage = totalPages;

        model.addAttribute("scheduleList", scheduleList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("countScheduleTotal", countScheduleTotal);
        model.addAttribute("countSchedulePage", countSchedulePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("finalPage", finalPage);

        return "mySchedules";
    }
}