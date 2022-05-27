package project.devsbarber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import project.devsbarber.model.User;
import project.devsbarber.repository.UserRepository;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/login")
    public String login(){
        return"login";
    }

    @RequestMapping("/")
    public String index(ModelMap model){
        Object userLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;
        if (userLogado instanceof UserDetails) {
            nome = ((UserDetails)userLogado).getUsername();
        } else {
            nome = userLogado.toString();
        }

        User user = userRepository.findByUsername(nome);

        if(user == null) {
            user = new User();
            user.setName(nome);
        }

        model.addAttribute("user",user);
        return "index";
    }

    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

    @RequestMapping("/agenda")
    public String agenda(ModelMap model){
        Object userLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;
        if (userLogado instanceof UserDetails) {
            nome = ((UserDetails)userLogado).getUsername();
        } else {
            nome = userLogado.toString();
        }

        User user = userRepository.findByUsername(nome);

        if(user == null) {
            user = new User();
            user.setName(nome);
        }

        model.addAttribute("user",user);
        return "agenda";
    }
}