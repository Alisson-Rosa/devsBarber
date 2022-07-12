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
import project.devsbarber.model.entities.Cut;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.enums.TypeCut;
import project.devsbarber.model.services.CutService;
import project.devsbarber.model.services.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CutController {

    @Autowired private UserService userService;
    @Autowired private CutService cutService;

    @RequestMapping(value = "/cuts")
    public String cutsIndex(){
        return "redirect:/cuts/1";
    }

    @RequestMapping(value = "/cuts/{pageId}")
    public String cuts(final Model model, @PathVariable int pageId) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        Page<Cut> cutsPagination = cutService.paginationCut(PageRequest.of(pageId - 1, 15, Sort.Direction.DESC, "typeCut"));
        List<Cut> cutList = cutsPagination.getContent();
        long countCutTotal = cutsPagination.getTotalElements();
        int totalPages = cutsPagination.getTotalPages();
        int countCutPage = cutsPagination.getNumberOfElements();
        int currentPage = cutsPagination.getNumber() + 1;
        int previousPage = currentPage - 1;
        int nextPage = currentPage + 1;
        int finalPage = totalPages;

        model.addAttribute("cutList", cutList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("countCutTotal", countCutTotal);
        model.addAttribute("countCutPage", countCutPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("finalPage", finalPage);

        return "cuts";
    }

    @RequestMapping(value = "/cuts/cutRegister")
    public String cutRegister(final Model model) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        Cut cutRegister = new Cut();
        model.addAttribute("cutRegister", cutRegister);

        TypeCut[] typeCuts = TypeCut.enumTypeCutAll();
        model.addAttribute("typeCuts", typeCuts);

        List<LocalTime> timeCutList = new ArrayList<>();
        LocalTime time = LocalTime.of(0,0);
        for(int i=0; i<12; i++){ //até 3h
            time = time.plusMinutes(15);
            timeCutList.add(time);
        }
        model.addAttribute("timeCutList", timeCutList);

        return "cutRegister";
    }

    @RequestMapping(method = RequestMethod.GET, value = "cuts/cutRegister/{cutId}")
    public String cutEdit(final Model model, @PathVariable("cutId") Long id) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        Cut cutRegister = cutService.get(id);
        model.addAttribute("cutRegister", cutRegister);

        TypeCut[] typeCuts = TypeCut.enumTypeCutAll();
        model.addAttribute("typeCuts", typeCuts);

        List<LocalTime> timeCutList = new ArrayList<>();
        LocalTime time = LocalTime.of(0,0);
        for(int i=0; i<12; i++){ //até 3h
            time = time.plusMinutes(15);
            timeCutList.add(time);
        }
        model.addAttribute("timeCutList", timeCutList);

        return "cutEdit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "cuts/cutRegister/createCut")
    public String createCut(@ModelAttribute Cut cutRegister) throws Exception {
        TypeCut typeCut = cutRegister.getTypeCut();
        boolean existTypeCut = cutService.existTypeCut(typeCut);
        if(existTypeCut){
            throw new Exception("Tipo de corte já cadastrado!"); //TODO Alterar para mensagem na tela
        }
        cutRegister.setEnable(true);
        cutService.saveCutRegister(cutRegister);
        return "redirect:/cuts/cutRegister/" + cutRegister.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "cuts/cutRegister/edit/{cutId}")
    public String cutEditResult(@ModelAttribute Cut cutEdit, @PathVariable("cutId") Long id){
        cutEdit.setId(id);
        cutService.saveCutRegister(cutEdit);
        return "redirect:/cuts/cutRegister/" + cutEdit.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "cuts/delete/{cutId}")
    public String deleteCut(@PathVariable("cutId") Long id) {

        cutService.deleteByid(id);

        return "redirect:/cuts";
    }

}
