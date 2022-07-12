package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.dto.CountTotalDTO;
import project.devsbarber.model.entities.*;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.repository.TimeKeyRepository;
import project.devsbarber.model.services.*;

import java.util.List;
import java.util.Objects;

@Controller
public class ScheduleReportController {

    @Autowired private UserService userService;
    @Autowired private ScheduleReportService scheduleReportService;
    @Autowired private BarberService barberService;
    @Autowired private TimeKeyService timeKeyService;

    private static ScheduleReportFilter filterStatic;

    @RequestMapping(method = RequestMethod.GET, value = "/scheduleReport")
    public String report() {
        return "redirect:/scheduleReport/1";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/scheduleReport/{pageId}")
    public String report(final Model model, @PathVariable int pageId, ScheduleReportFilter filter){

        filter = filterStatic != null ? filterStatic : new ScheduleReportFilter();

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);


        model.addAttribute("filter", filter);

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

        List<Schedule> scheduleListTotal = scheduleReportService.findByIds(ids);
        CountTotalDTO totalDTO = scheduleReportService.countTotais(scheduleListTotal);
        model.addAttribute("totalDTO", totalDTO);

        List<Barber> barberList = barberService.findAll();
        model.addAttribute("barberList", barberList);

        Long initialBusinessHours = 25L; //TODO Alterar para parametro da tela de configs
        Long finalBusinessHours = 85L;
        List<TimeKey> timeKeyList = timeKeyService.findByInitialAndFinalKeys(initialBusinessHours, finalBusinessHours);
        model.addAttribute("timeKeyList", timeKeyList);

        TypeCut[] typeCuts = TypeCut.enumTypeCutAll();
        model.addAttribute("typeCutList", typeCuts);

        return "scheduleReport";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/scheduleReportFilter/{pageId}")
    public String reportFilter(@ModelAttribute ScheduleReportFilter filter, final Model model, @PathVariable int pageId){
        filterStatic = filter;
        return report(model, pageId, filter);
    }

}
