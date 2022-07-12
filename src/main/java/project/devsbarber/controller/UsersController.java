package project.devsbarber.controller;

import org.apache.juli.logging.Log;
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
import project.devsbarber.model.dto.UserRegisterDTO;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.services.RoleService;
import project.devsbarber.model.services.UserService;

import java.util.List;

@Controller
public class UsersController {

    @Autowired private RoleService roleService;

    @Autowired private UserService userService;

    @RequestMapping(value = "/users")
    public String usersIndex(){
        return "redirect:/users/1";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{pageId}")
    public String users(final Model model, @PathVariable int pageId) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

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

        UserRegisterDTO userDTO = new UserRegisterDTO();
        model.addAttribute("userDTO", userDTO);

//        User userRegister = new User();
//        Long userRoleId = null;
//        model.addAttribute("userRegister", userRegister);
//        model.addAttribute("userRoleId", userRoleId);

        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList", roleList);
        return "internalUserRegister";
    }

    @RequestMapping(method = RequestMethod.GET, value = "users/userRegister/{userId}")
    public String usersEdit(final Model model, @PathVariable("userId") Long id) {

        User userLogado = userService.getUserLogado();
        model.addAttribute("userLogado", userLogado);

        UserRegisterDTO userDTO = userService.getUserDTO(id);
        model.addAttribute("userDTO", userDTO);

        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList", roleList);

        return "InternalUserEdit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit/{userId}")
    public String userEditResult(@ModelAttribute UserRegisterDTO userDTO, @PathVariable("userId") Long id, final Model model){

        try{
            String newPassword = userDTO.getNewPassword();
            if(newPassword != null && !newPassword.equals("")){
                String password = userDTO.getPassword();
                Long userId = userDTO.getUserId();
                boolean validatePassword = userService.validatePassword(userId, password);

                if(!validatePassword){
                    model.addAttribute("erro", "senha incorreta!");

                    User userLogado = userService.getUserLogado();
                    model.addAttribute("userLogado", userLogado);

                    model.addAttribute("userDTO", userDTO);

                    List<Role> roleList = roleService.findAll();
                    model.addAttribute("roleList", roleList);
                    return "InternalUserEdit";
                }

            }

            userDTO.setUserId(id);
            if(userDTO.getBirthdate() == null) {
                User user = userService.getUser(id);
                userDTO.setBirthdate(user.getBirthdate());
            }

            userService.saveOrUpdateUserDTO(userDTO);

            model.addAttribute("success", "alteração realizada com sucesso!");

            User userLogado = userService.getUserLogado();
            model.addAttribute("userLogado", userLogado);

            model.addAttribute("userDTO", userDTO);

            List<Role> roleList = roleService.findAll();
            model.addAttribute("roleList", roleList);
            return "InternalUserEdit";

        } catch (Exception e){
            //TODO criar log
            return "redirect:/users/userRegister/" + userDTO.getUserId();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "users/create")
    public String create(@ModelAttribute UserRegisterDTO userRegisterDTO) throws Exception {
        String username = userRegisterDTO.getUsername();
        boolean existUsername = userService.existUsername(username);
        if(existUsername){
            throw new Exception("Nome de usuario já cadastrado"); //TODO Alterar para mensagem na tela
        }
        userRegisterDTO.setEnable(true);
        User user = userService.saveOrUpdateUserDTO(userRegisterDTO);
        return "redirect:/users/userRegister/" + user.getId();
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
