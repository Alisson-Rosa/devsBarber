package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.services.UserService;
import project.devsbarber.repository.UserRepository;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(){
        return"login";
    }

    @RequestMapping("/")
    public String index(ModelMap model){
        User user = userService.getUserLogado();

        model.addAttribute("user",user);
        return "index";
    }

    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

}