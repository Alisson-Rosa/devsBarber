package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.services.UserService;
import project.devsbarber.model.repository.RoleRepository;
import project.devsbarber.model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserFormController {

    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/userForm")
    public String usersForm(final Model model) {
        model.addAttribute("user", new User());
        Double teste = 521412412.0;
        model.addAttribute("teste", teste);
        return "userForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uerForm/register")
    public String result(@ModelAttribute User user, final Model model, BindingResult bindingResult) throws Exception {

        Map<String, Object> modelTeste = bindingResult.getModel();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (!allErrors.isEmpty()){
            model.addAttribute("erro", "erro ao fazer cadastro!");
            model.addAttribute("user", new User());
            return "userForm";
        }

        try {
            Role userRole = roleRepository.getByName("CLIENT");
            List<String> sucess = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            boolean existUsername = userService.existUsername(user.getUsername());
            if(existUsername){
                 errors.add("Usuario já cadastrado!");
                 model.addAttribute("user", new User());
                 model.addAttribute("erro", errors);
                 return "userForm";
            }

            user.setRole(userRole);
            user.setEnable(true);
            userRepository.save(user);

            sucess.add("Cadastro realizado com sucesso!");
            return "redirect:/login?successRegister";

        } catch (Exception e){
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("user", new User());
            return "userForm";
        }
    }
}