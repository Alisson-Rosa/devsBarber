package project.devsbarber.controller;

import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.services.UserService;
import project.devsbarber.repository.RoleRepository;

import java.util.List;

@Controller
public class userRegisterController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users")
    public String users(final Model model) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        User userRegister = new User();
        model.addAttribute("userRegister", userRegister);

        List<Role> roleList = (List<Role>) roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        return "users";
    }

    @RequestMapping(value = "/users/userRegister")
    public String usersRegister(final Model model) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        User userRegister = new User();
        Role role = new Role();
        model.addAttribute("userRegister", userRegister);
        model.addAttribute("userRole", role);

        List<Role> roleList = (List<Role>) roleRepository.findAll();
        model.addAttribute("roleList", roleList);
        return "internalUserRegister";
    }

    @RequestMapping(method = RequestMethod.GET, value = "users/userRegister/{userId}")
    public String usersEdit(final Model model, @PathVariable("userId") Long id) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        User userRegister = userService.getUser(id);
        Role role = new Role();
        model.addAttribute("userRegister", userRegister);
        model.addAttribute("userRole", role);

        List<Role> roleList = (List<Role>) roleRepository.findAll();
        model.addAttribute("roleList", roleList);

        return "InternalUserEdit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit/{userId}")
    public String userEdit(@ModelAttribute User userRegister, @ModelAttribute Role userRole, @PathVariable("userId") Long id){
        userRegister.setId(id);
        if(userRegister.getRole() != userRole){
            userRegister.setRole(userRole);
        }
        User user = userService.getUser(id);
        user.setBirthdate(userRegister.getBirthdate());
        userService.saveOrUpdate(user);
        return "redirect:/users/userRegister/" + userRegister.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public String create(@ModelAttribute User userRegister) throws Exception {//TODO Não está funcionando
        String username = userRegister.getUsername();
        boolean existUsername = userService.existUsername(username);//TODO Alterar para findByUsername
        if(existUsername){
            throw new Exception("Nome de usuario já cadastrado"); //TODO Alterar para mensagem na tela
        }
        userService.saveOrUpdate(userRegister);
        return "redirect:/users/userRegister/" + userRegister.getId();
    }
}
