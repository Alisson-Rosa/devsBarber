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
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.services.UserService;
import project.devsbarber.model.repository.RoleRepository;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users")
    public String usersIndex(){
        return "redirect:/users/1";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{pageId}")
    public String users(final Model model, @PathVariable int pageId) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        User userRegister = new User();
        model.addAttribute("userRegister", userRegister);

        Page<User> usersPagination = userService.paginationUser(PageRequest.of(pageId - 1, 15, Sort.Direction.DESC, "username"));
        List<User> userList = usersPagination.getContent();
        long countUserTotal = usersPagination.getTotalElements();
        int totalPages = usersPagination.getTotalPages();
        int countUserPage = usersPagination.getNumberOfElements();
        int currentPage = usersPagination.getNumber() + 1;
        int previousPage = currentPage - 1;
        int nextPage = currentPage + 1;
        int finalPage = totalPages;

        model.addAttribute("userList", userList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("countUserTotal", countUserTotal);
        model.addAttribute("countUserPage", countUserPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("finalPage", finalPage);


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
    public String userEditResult(@ModelAttribute User userRegister, @ModelAttribute Role userRole, @PathVariable("userId") Long id){
        userRegister.setId(id);
        if(userRegister.getRole() != userRole){
            userRegister.setRole(userRole);
        }
        if(userRegister.getBirthdate() == null) {
            User user = userService.getUser(id);
            userRegister.setBirthdate(user.getBirthdate());
        }
        userService.saveOrUpdate(userRegister);
        return "redirect:/users/userRegister/" + userRegister.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "users/create")
    public String create(@ModelAttribute User userRegister, @ModelAttribute Role userRole) throws Exception {
        userRegister.setRole(userRole);
        String username = userRegister.getUsername();
        boolean existUsername = userService.existUsername(username);
        if(existUsername){
            throw new Exception("Nome de usuario já cadastrado"); //TODO Alterar para mensagem na tela
        }
        userRegister.setTelephone("41 99999-999");
        userService.saveOrUpdate(userRegister);
        return "redirect:/users/userRegister/" + userRegister.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "users/delete/{userId}")
    public String deleteBarber(@PathVariable("userId") Long id) {

        try{
            userService.deleteByid(id);
        } catch (Exception e){
            throw e; //TODO alterar para mensagem na tela
        }

        return "redirect:/users";
    }
}
