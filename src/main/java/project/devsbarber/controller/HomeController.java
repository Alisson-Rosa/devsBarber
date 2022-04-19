package project.devsbarber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/login")
    public String login(){
        return"login";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

    @RequestMapping("/agenda")
    public String agenda(){
        return "agenda";
    }
}